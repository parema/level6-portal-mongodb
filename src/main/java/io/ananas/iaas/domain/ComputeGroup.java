package io.ananas.iaas.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ComputeGroup.
 */
@Document(collection = "compute_group")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "computegroup")
public class ComputeGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @Field("compute_group_name")
    private String computeGroupName;

    @DBRef
    @Field("parent")
    @JsonIgnoreProperties("computeGroups")
    private Project parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComputeGroupName() {
        return computeGroupName;
    }

    public ComputeGroup computeGroupName(String computeGroupName) {
        this.computeGroupName = computeGroupName;
        return this;
    }

    public void setComputeGroupName(String computeGroupName) {
        this.computeGroupName = computeGroupName;
    }

    public Project getParent() {
        return parent;
    }

    public ComputeGroup parent(Project project) {
        this.parent = project;
        return this;
    }

    public void setParent(Project project) {
        this.parent = project;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComputeGroup)) {
            return false;
        }
        return id != null && id.equals(((ComputeGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ComputeGroup{" +
            "id=" + getId() +
            ", computeGroupName='" + getComputeGroupName() + "'" +
            "}";
    }
}
