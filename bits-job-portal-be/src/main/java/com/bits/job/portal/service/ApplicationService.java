package com.bits.job.portal.service;

import com.bits.job.portal.enums.ApplicationStatus;
import com.bits.job.portal.exception.EntityNotFoundException;
import com.bits.job.portal.model.JobItem;
import com.bits.job.portal.model.Application;
import com.bits.job.portal.model.ApplicationItem;
import com.bits.job.portal.model.Employer;
import com.bits.job.portal.repository.JobItemRepository;
import com.bits.job.portal.repository.ApplicationRepository;
import com.bits.job.portal.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final EmployerRepository employerRepository;
    private final JobItemRepository jobItemRepository;

    public Application placeApplication(Application application) {
        employerRepository.findById(application.getEmployerId()).orElseThrow(() ->
                new EntityNotFoundException("Employer with ID " + application.getEmployerId() + " not found"));

        double salary = 0;
        ApplicationItem applicationItem = application.getApplicationItem();
            JobItem jobItem = jobItemRepository.findById(applicationItem.getJobItemId()).orElseThrow(() ->
                    new EntityNotFoundException("JobItem with ID " + applicationItem.getJobItemId() + " not found"));

            if (!jobItem.getAvailability()) {
                throw new IllegalStateException("JobItem with ID " + applicationItem.getJobItemId() + " is not available");
            }

        salary = jobItem.getSalary();
        application.setStatus(ApplicationStatus.APPLIED);
        application.setSalary(salary);
        application.setApplicationItem(applicationItem);
        application.setCreatedDate(LocalDateTime.now());
        return applicationRepository.save(application);
    }

    public Application getApplicationById(String applicationId) {
        return applicationRepository.findById(applicationId).orElseThrow(() ->
                new EntityNotFoundException("Application with ID " + applicationId + " not found"));
    }

    public List<Application> getApplicationHistory(String employeeId) {
        return applicationRepository.findByEmployeeIdOrderByCreatedAtDesc(employeeId);
    }

    public Application updateApplicationStatus(String applicationId, ApplicationStatus status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));
        application.setStatus(status);
        return applicationRepository.save(application);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
}