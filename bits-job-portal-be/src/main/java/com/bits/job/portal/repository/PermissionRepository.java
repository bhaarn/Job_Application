package com.bits.job.portal.repository;

import com.bits.job.portal.model.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PermissionRepository extends MongoRepository<Permission, String> {
    Permission findByName(String name);
}
