package com.bits.job.portal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "permissions")
public class Permission {
    @Id
    private String id;
    private String name;
}
