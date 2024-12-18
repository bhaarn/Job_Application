package com.bits.job.portal.repository;


import com.bits.job.portal.enums.JobItemType;
import com.bits.job.portal.model.Employer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployerRepository extends MongoRepository<Employer, String> {

    Optional<Employer> findByName(String name);

    @Query("{ 'jobItems': { $elemMatch: { 'type': ?0, 'name': { $regex: ?1, $options: 'i' } } } }")
    List<Employer> findByJobItemTypeAndName(JobItemType type, String name);
}