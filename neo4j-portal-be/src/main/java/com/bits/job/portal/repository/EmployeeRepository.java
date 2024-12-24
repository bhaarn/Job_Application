package com.bits.job.portal.repository;

import com.bits.job.portal.model.Candidate;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface EmployeeRepository extends Neo4jRepository<Candidate, String> {
    Candidate findByName(String name); // Custom query method for finding by name
}
