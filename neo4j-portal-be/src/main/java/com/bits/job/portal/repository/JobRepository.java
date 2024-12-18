package com.bits.job.portal.repository;

import com.bits.job.portal.model.Job;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface JobRepository extends Neo4jRepository<Job, String> {
    Job findByTitle(String title); // Custom query method for finding by job title
}
