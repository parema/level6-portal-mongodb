package io.ananas.iaas.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Project.
 */
@Document(collection = "project")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @Field("project_name")
    private String projectName;

    @DBRef
    @Field("computeGroups")
    private Set<ComputeGroup> computeGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public Project projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Set<ComputeGroup> getComputeGroups() {
        return computeGroups;
    }

    public Project computeGroups(Set<ComputeGroup> computeGroups) {
        this.computeGroups = computeGroups;
        return this;
    }

    public Project addComputeGroups(ComputeGroup computeGroup) {
        this.computeGroups.add(computeGroup);
        computeGroup.setParent(this);
        return this;
    }

    public Project removeComputeGroups(ComputeGroup computeGroup) {
        this.computeGroups.remove(computeGroup);
        computeGroup.setParent(null);
        return this;
    }

    public void setComputeGroups(Set<ComputeGroup> computeGroups) {
        this.computeGroups = computeGroups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", projectName='" + getProjectName() + "'" +
            "}";
    }
}
