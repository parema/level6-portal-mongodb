package io.ananas.iaas.repository;

import io.ananas.iaas.domain.ComputeGroup;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the ComputeGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComputeGroupRepository extends MongoRepository<ComputeGroup, String> {

}
