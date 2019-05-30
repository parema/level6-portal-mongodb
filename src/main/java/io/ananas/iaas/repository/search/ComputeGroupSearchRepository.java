package io.ananas.iaas.repository.search;

import io.ananas.iaas.domain.ComputeGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ComputeGroup} entity.
 */
public interface ComputeGroupSearchRepository extends ElasticsearchRepository<ComputeGroup, String> {
}
