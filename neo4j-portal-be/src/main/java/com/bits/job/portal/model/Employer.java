package com.bits.job.portal.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node
public class Employer {

    @Id
    private String name;

    private String company;

    @JsonManagedReference
    @Relationship(type = "POSTED", direction = Relationship.Direction.OUTGOING)
    private List<Job> jobs; // Multiple jobs that the employer has posted
}
