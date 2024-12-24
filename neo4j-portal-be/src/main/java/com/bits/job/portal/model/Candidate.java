package com.bits.job.portal.model;

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
public class Candidate {

    @Id
    private String name;

    private String emailAddress;

    @Relationship(type = "APPLIED_FOR")
    private Job job;
}

