package com.bits.job.portal.controller;

import com.bits.job.portal.dto.UpdateApplicationStatusRequest;
import com.bits.job.portal.model.Application;
import com.bits.job.portal.model.Employer;
import com.bits.job.portal.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
@Validated
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Application> placeApplication(@Valid @RequestBody Application application) {
        Application AppliedApplication = applicationService.placeApplication(application);
        return ResponseEntity.ok(AppliedApplication);
    }

    @GetMapping("/{applicationId}/status")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'EMPLOYER')")
    public ResponseEntity<Application> getApplicationStatus(@PathVariable String applicationId) {
        return ResponseEntity.ok(applicationService.getApplicationById(applicationId));
    }

    @PutMapping("/{applicationId}/status")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<Application> updateApplicationStatus(@PathVariable String applicationId, @RequestBody UpdateApplicationStatusRequest request) {
        Application updatedApplication = applicationService.updateApplicationStatus(applicationId, request.status());
        return ResponseEntity.ok(updatedApplication);
    }

    @GetMapping("/getallapplication")
    @PreAuthorize("hasAnyRole('EMPLOYER', 'ADMIN')")
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }
}