package com.bits.job.portal.repository;

import com.bits.job.portal.model.Employer;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface EmployerRepository extends Neo4jRepository<Employer, String> {
    Employer findByName(String name); // Custom query method for finding by name
}
