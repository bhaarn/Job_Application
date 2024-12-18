package com.bits.job.portal.repository;

import com.bits.job.portal.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {
    Job findByEmployerId(String employerId);
}