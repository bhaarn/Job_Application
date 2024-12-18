package com.bits.job.portal.model;

import com.bits.job.portal.enums.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "applications")
public class Application {
    @Id
    private String id;

    @NotBlank(message = "Employee ID is mandatory")
    private String employeeId;

    @NotBlank(message = "Employer ID is mandatory")
    private String employerId;

    @NotNull(message = "Application Item is mandatory")
    private ApplicationItem applicationItem;

    private ApplicationStatus status;

    private Double salary;

    @CreatedDate
    private LocalDateTime createdDate;
}