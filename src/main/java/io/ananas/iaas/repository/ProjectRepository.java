package io.ananas.iaas.repository;

import io.ananas.iaas.domain.Project;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

}
