package com.bits.job.portal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node
public class Job {

    @Id
    private String title;

    private String description;

    @JsonBackReference
    @Relationship(type = "POSTED", direction = Relationship.Direction.INCOMING)
    private Employer employer; // The employer who posted this job

    @Relationship(type = "APPLIED_FOR", direction = Relationship.Direction.OUTGOING)
    private Employee employee; // The employee who applied for the job
}
