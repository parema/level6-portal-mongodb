package io.ananas.iaas.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ComputeGroupSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ComputeGroupSearchRepositoryMockConfiguration {

    @MockBean
    private ComputeGroupSearchRepository mockComputeGroupSearchRepository;

}
