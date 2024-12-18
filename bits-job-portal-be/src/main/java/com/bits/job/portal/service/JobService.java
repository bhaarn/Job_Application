package com.bits.job.portal.service;

import com.bits.job.portal.exception.EntityNotFoundException;
import com.bits.job.portal.model.Job;
import com.bits.job.portal.model.JobItem;
import com.bits.job.portal.repository.JobItemRepository;
import com.bits.job.portal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final JobItemRepository jobItemRepository;

    public JobItem createJobItem(String employerId, JobItem jobItem) {
        Job job = jobRepository.findByEmployerId(employerId);
        if (job == null) {
            throw new EntityNotFoundException("Job for Employer with ID " + employerId + " not found");
        }
        jobItemRepository.save(jobItem);
        job.getItems().add(jobItem);
        jobRepository.save(job);
        return jobItem;
    }

    public void deleteJobItem(String employerId, String jobItemId) {
        Job job = jobRepository.findByEmployerId(employerId);
        if (job == null) {
            throw new EntityNotFoundException("Job for Employer with ID " + employerId + " not found");
        }
        JobItem jobItem = jobItemRepository.findById(jobItemId).orElseThrow(() ->
                new EntityNotFoundException("JobItem with ID " + jobItemId + " not found"));
        job.getItems().remove(jobItem);
        jobItemRepository.delete(jobItem);
        jobRepository.save(job);
    }

    public JobItem updateJobItem(String employerId, String jobItemId, JobItem updatedItem) {
        Job job = jobRepository.findByEmployerId(employerId);
        if (job == null) {
            throw new EntityNotFoundException("Job for Employer with ID " + employerId + " not found");
        }
        JobItem jobItem = jobItemRepository.findById(jobItemId).orElseThrow(() ->
                new EntityNotFoundException("JobItem with ID " + jobItemId + " not found"));

        jobItem.setName(updatedItem.getName());
        jobItem.setDescription(updatedItem.getDescription());
        jobItem.setSalary(updatedItem.getSalary());
        jobItem.setAvailability(updatedItem.getAvailability());

        return jobItemRepository.save(jobItem);
    }

    public Job getJobByEmployerId(String employerId) {
        Job job = jobRepository.findByEmployerId(employerId);
        if (job == null) {
            throw new EntityNotFoundException("Job for Employer with ID " + employerId + " not found");
        }
        return job;
    }

    public Job createJob(Job job) {
        List<JobItem> jobItems = job.getItems();
        jobItemRepository.saveAll(jobItems);
        return jobRepository.save(job);
    }
}