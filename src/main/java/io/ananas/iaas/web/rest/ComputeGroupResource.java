package io.ananas.iaas.web.rest;

import io.ananas.iaas.domain.ComputeGroup;
import io.ananas.iaas.repository.ComputeGroupRepository;
import io.ananas.iaas.repository.search.ComputeGroupSearchRepository;
import io.ananas.iaas.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link io.ananas.iaas.domain.ComputeGroup}.
 */
@RestController
@RequestMapping("/api")
public class ComputeGroupResource {

    private final Logger log = LoggerFactory.getLogger(ComputeGroupResource.class);

    private static final String ENTITY_NAME = "computeGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComputeGroupRepository computeGroupRepository;

    private final ComputeGroupSearchRepository computeGroupSearchRepository;

    public ComputeGroupResource(ComputeGroupRepository computeGroupRepository, ComputeGroupSearchRepository computeGroupSearchRepository) {
        this.computeGroupRepository = computeGroupRepository;
        this.computeGroupSearchRepository = computeGroupSearchRepository;
    }

    /**
     * {@code POST  /compute-groups} : Create a new computeGroup.
     *
     * @param computeGroup the computeGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new computeGroup, or with status {@code 400 (Bad Request)} if the computeGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compute-groups")
    public ResponseEntity<ComputeGroup> createComputeGroup(@RequestBody ComputeGroup computeGroup) throws URISyntaxException {
        log.debug("REST request to save ComputeGroup : {}", computeGroup);
        if (computeGroup.getId() != null) {
            throw new BadRequestAlertException("A new computeGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComputeGroup result = computeGroupRepository.save(computeGroup);
        computeGroupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/compute-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compute-groups} : Updates an existing computeGroup.
     *
     * @param computeGroup the computeGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated computeGroup,
     * or with status {@code 400 (Bad Request)} if the computeGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the computeGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compute-groups")
    public ResponseEntity<ComputeGroup> updateComputeGroup(@RequestBody ComputeGroup computeGroup) throws URISyntaxException {
        log.debug("REST request to update ComputeGroup : {}", computeGroup);
        if (computeGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComputeGroup result = computeGroupRepository.save(computeGroup);
        computeGroupSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, computeGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /compute-groups} : get all the computeGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of computeGroups in body.
     */
    @GetMapping("/compute-groups")
    public List<ComputeGroup> getAllComputeGroups() {
        log.debug("REST request to get all ComputeGroups");
        return computeGroupRepository.findAll();
    }

    /**
     * {@code GET  /compute-groups/:id} : get the "id" computeGroup.
     *
     * @param id the id of the computeGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the computeGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compute-groups/{id}")
    public ResponseEntity<ComputeGroup> getComputeGroup(@PathVariable String id) {
        log.debug("REST request to get ComputeGroup : {}", id);
        Optional<ComputeGroup> computeGroup = computeGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(computeGroup);
    }

    /**
     * {@code DELETE  /compute-groups/:id} : delete the "id" computeGroup.
     *
     * @param id the id of the computeGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compute-groups/{id}")
    public ResponseEntity<Void> deleteComputeGroup(@PathVariable String id) {
        log.debug("REST request to delete ComputeGroup : {}", id);
        computeGroupRepository.deleteById(id);
        computeGroupSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/compute-groups?query=:query} : search for the computeGroup corresponding
     * to the query.
     *
     * @param query the query of the computeGroup search.
     * @return the result of the search.
     */
    @GetMapping("/_search/compute-groups")
    public List<ComputeGroup> searchComputeGroups(@RequestParam String query) {
        log.debug("REST request to search ComputeGroups for query {}", query);
        return StreamSupport
            .stream(computeGroupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
