package com.bits.job.portal.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationItem {
    @NotBlank(message = "Job Item ID is mandatory")
    private String jobItemId;
}