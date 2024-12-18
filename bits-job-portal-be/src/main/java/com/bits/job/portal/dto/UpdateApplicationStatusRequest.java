package com.bits.job.portal.dto;

import com.bits.job.portal.enums.ApplicationStatus;


public record UpdateApplicationStatusRequest(ApplicationStatus status) {
}
