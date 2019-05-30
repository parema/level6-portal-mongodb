package io.ananas.iaas.web.rest;

import io.ananas.iaas.Level6App;
import io.ananas.iaas.config.TestSecurityConfiguration;
import io.ananas.iaas.domain.ComputeGroup;
import io.ananas.iaas.repository.ComputeGroupRepository;
import io.ananas.iaas.repository.search.ComputeGroupSearchRepository;
import io.ananas.iaas.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.util.Collections;
import java.util.List;

import static io.ananas.iaas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ComputeGroupResource} REST controller.
 */
@SpringBootTest(classes = {Level6App.class, TestSecurityConfiguration.class})
public class ComputeGroupResourceIT {

    private static final String DEFAULT_COMPUTE_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPUTE_GROUP_NAME = "BBBBBBBBBB";

    @Autowired
    private ComputeGroupRepository computeGroupRepository;

    /**
     * This repository is mocked in the io.ananas.iaas.repository.search test package.
     *
     * @see io.ananas.iaas.repository.search.ComputeGroupSearchRepositoryMockConfiguration
     */
    @Autowired
    private ComputeGroupSearchRepository mockComputeGroupSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restComputeGroupMockMvc;

    private ComputeGroup computeGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComputeGroupResource computeGroupResource = new ComputeGroupResource(computeGroupRepository, mockComputeGroupSearchRepository);
        this.restComputeGroupMockMvc = MockMvcBuilders.standaloneSetup(computeGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComputeGroup createEntity() {
        ComputeGroup computeGroup = new ComputeGroup()
            .computeGroupName(DEFAULT_COMPUTE_GROUP_NAME);
        return computeGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComputeGroup createUpdatedEntity() {
        ComputeGroup computeGroup = new ComputeGroup()
            .computeGroupName(UPDATED_COMPUTE_GROUP_NAME);
        return computeGroup;
    }

    @BeforeEach
    public void initTest() {
        computeGroupRepository.deleteAll();
        computeGroup = createEntity();
    }

    @Test
    public void createComputeGroup() throws Exception {
        int databaseSizeBeforeCreate = computeGroupRepository.findAll().size();

        // Create the ComputeGroup
        restComputeGroupMockMvc.perform(post("/api/compute-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(computeGroup)))
            .andExpect(status().isCreated());

        // Validate the ComputeGroup in the database
        List<ComputeGroup> computeGroupList = computeGroupRepository.findAll();
        assertThat(computeGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ComputeGroup testComputeGroup = computeGroupList.get(computeGroupList.size() - 1);
        assertThat(testComputeGroup.getComputeGroupName()).isEqualTo(DEFAULT_COMPUTE_GROUP_NAME);

        // Validate the ComputeGroup in Elasticsearch
        verify(mockComputeGroupSearchRepository, times(1)).save(testComputeGroup);
    }

    @Test
    public void createComputeGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = computeGroupRepository.findAll().size();

        // Create the ComputeGroup with an existing ID
        computeGroup.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restComputeGroupMockMvc.perform(post("/api/compute-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(computeGroup)))
            .andExpect(status().isBadRequest());

        // Validate the ComputeGroup in the database
        List<ComputeGroup> computeGroupList = computeGroupRepository.findAll();
        assertThat(computeGroupList).hasSize(databaseSizeBeforeCreate);

        // Validate the ComputeGroup in Elasticsearch
        verify(mockComputeGroupSearchRepository, times(0)).save(computeGroup);
    }


    @Test
    public void getAllComputeGroups() throws Exception {
        // Initialize the database
        computeGroupRepository.save(computeGroup);

        // Get all the computeGroupList
        restComputeGroupMockMvc.perform(get("/api/compute-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(computeGroup.getId())))
            .andExpect(jsonPath("$.[*].computeGroupName").value(hasItem(DEFAULT_COMPUTE_GROUP_NAME.toString())));
    }
    
    @Test
    public void getComputeGroup() throws Exception {
        // Initialize the database
        computeGroupRepository.save(computeGroup);

        // Get the computeGroup
        restComputeGroupMockMvc.perform(get("/api/compute-groups/{id}", computeGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(computeGroup.getId()))
            .andExpect(jsonPath("$.computeGroupName").value(DEFAULT_COMPUTE_GROUP_NAME.toString()));
    }

    @Test
    public void getNonExistingComputeGroup() throws Exception {
        // Get the computeGroup
        restComputeGroupMockMvc.perform(get("/api/compute-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateComputeGroup() throws Exception {
        // Initialize the database
        computeGroupRepository.save(computeGroup);

        int databaseSizeBeforeUpdate = computeGroupRepository.findAll().size();

        // Update the computeGroup
        ComputeGroup updatedComputeGroup = computeGroupRepository.findById(computeGroup.getId()).get();
        updatedComputeGroup
            .computeGroupName(UPDATED_COMPUTE_GROUP_NAME);

        restComputeGroupMockMvc.perform(put("/api/compute-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComputeGroup)))
            .andExpect(status().isOk());

        // Validate the ComputeGroup in the database
        List<ComputeGroup> computeGroupList = computeGroupRepository.findAll();
        assertThat(computeGroupList).hasSize(databaseSizeBeforeUpdate);
        ComputeGroup testComputeGroup = computeGroupList.get(computeGroupList.size() - 1);
        assertThat(testComputeGroup.getComputeGroupName()).isEqualTo(UPDATED_COMPUTE_GROUP_NAME);

        // Validate the ComputeGroup in Elasticsearch
        verify(mockComputeGroupSearchRepository, times(1)).save(testComputeGroup);
    }

    @Test
    public void updateNonExistingComputeGroup() throws Exception {
        int databaseSizeBeforeUpdate = computeGroupRepository.findAll().size();

        // Create the ComputeGroup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComputeGroupMockMvc.perform(put("/api/compute-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(computeGroup)))
            .andExpect(status().isBadRequest());

        // Validate the ComputeGroup in the database
        List<ComputeGroup> computeGroupList = computeGroupRepository.findAll();
        assertThat(computeGroupList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ComputeGroup in Elasticsearch
        verify(mockComputeGroupSearchRepository, times(0)).save(computeGroup);
    }

    @Test
    public void deleteComputeGroup() throws Exception {
        // Initialize the database
        computeGroupRepository.save(computeGroup);

        int databaseSizeBeforeDelete = computeGroupRepository.findAll().size();

        // Delete the computeGroup
        restComputeGroupMockMvc.perform(delete("/api/compute-groups/{id}", computeGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ComputeGroup> computeGroupList = computeGroupRepository.findAll();
        assertThat(computeGroupList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ComputeGroup in Elasticsearch
        verify(mockComputeGroupSearchRepository, times(1)).deleteById(computeGroup.getId());
    }

    @Test
    public void searchComputeGroup() throws Exception {
        // Initialize the database
        computeGroupRepository.save(computeGroup);
        when(mockComputeGroupSearchRepository.search(queryStringQuery("id:" + computeGroup.getId())))
            .thenReturn(Collections.singletonList(computeGroup));
        // Search the computeGroup
        restComputeGroupMockMvc.perform(get("/api/_search/compute-groups?query=id:" + computeGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(computeGroup.getId())))
            .andExpect(jsonPath("$.[*].computeGroupName").value(hasItem(DEFAULT_COMPUTE_GROUP_NAME)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComputeGroup.class);
        ComputeGroup computeGroup1 = new ComputeGroup();
        computeGroup1.setId("id1");
        ComputeGroup computeGroup2 = new ComputeGroup();
        computeGroup2.setId(computeGroup1.getId());
        assertThat(computeGroup1).isEqualTo(computeGroup2);
        computeGroup2.setId("id2");
        assertThat(computeGroup1).isNotEqualTo(computeGroup2);
        computeGroup1.setId(null);
        assertThat(computeGroup1).isNotEqualTo(computeGroup2);
    }
}
