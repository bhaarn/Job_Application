package com.bits.job.portal.repository;

import com.bits.job.portal.model.JobItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobItemRepository extends MongoRepository<JobItem, String> {
}