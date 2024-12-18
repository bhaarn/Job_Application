package com.bits.job.portal.repository;

import com.bits.job.portal.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ApplicationRepository extends MongoRepository<Application, String> {

    @Query(value = "{ 'employeeId': ?0 }", sort = "{ 'createdDate': -1 }")
    List<Application> findByEmployeeIdOrderByCreatedAtDesc(String employeeId);
}