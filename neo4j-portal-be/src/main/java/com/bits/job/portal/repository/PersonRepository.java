package com.bits.job.portal.repository;

import com.bits.job.portal.model.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PersonRepository extends Neo4jRepository<Person, String> {
    Person findByName(String name); // Custom query method for finding by name
}
