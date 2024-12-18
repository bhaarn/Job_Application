package com.bits.job.portal.service;

import com.bits.job.portal.exception.EntityNotFoundException;
import com.bits.job.portal.model.Job;
import com.bits.job.portal.model.JobItem;
import com.bits.job.portal.repository.JobItemRepository;
import com.bits.job.portal.repository.JobRepository;
import com.bits.job.portal.repository.EmployerRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JobServiceTest {

    @Autowired
    private JobService jobService;

    @MockBean
    private JobRepository jobRepository;

    @MockBean
    private JobItemRepository jobItemRepository;

    @MockBean
    private EmployerRepository employerRepository;

    @Test
    public void testCreateJobItem() {
        Job job = new Job();
        JobItem jobItem = new JobItem();
        job.setItems(new ArrayList<>());
        jobItem.setId("1");
        jobItem.setName("Test Item");

        Mockito.when(jobRepository.findByEmployerId(anyString())).thenReturn(job);
        Mockito.when(jobItemRepository.save(any(JobItem.class))).thenReturn(jobItem);

        JobItem createdItem = jobService.createJobItem("employerId", jobItem);

        assertThat(createdItem).isNotNull();
        assertThat(createdItem.getName()).isEqualTo("Test Item");
    }

    @Test
    public void testCreateJobItemJobNotFound() {
        Mockito.when(jobRepository.findByEmployerId(anyString())).thenReturn(null);

        JobItem jobItem = new JobItem();
        assertThrows(EntityNotFoundException.class, () -> {
            jobService.createJobItem("employerId", jobItem);
        });
    }

    @Test
    public void testDeleteJobItem() {
        Job job = new Job();
        JobItem jobItem = new JobItem();
        jobItem.setId("1");
        job.setItems(new ArrayList<>());

        Mockito.when(jobRepository.findByEmployerId(anyString())).thenReturn(job);
        Mockito.when(jobItemRepository.findById(anyString())).thenReturn(Optional.of(jobItem));

        jobService.deleteJobItem("employerId", "jobItemId");

        Mockito.verify(jobItemRepository, Mockito.times(1)).delete(jobItem);
    }

    @Test
    public void testDeleteJobItemNotFound() {
        Mockito.when(jobRepository.findByEmployerId(anyString())).thenReturn(new Job());
        Mockito.when(jobItemRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            jobService.deleteJobItem("employerId", "jobItemId");
        });
    }

    @Test
    public void testUpdateJobItem() {
        Job job = new Job();
        JobItem jobItem = new JobItem();
        jobItem.setId("1");
        jobItem.setName("Updated Item");

        Mockito.when(jobRepository.findByEmployerId(anyString())).thenReturn(job);
        Mockito.when(jobItemRepository.findById(anyString())).thenReturn(Optional.of(jobItem));
        Mockito.when(jobItemRepository.save(any(JobItem.class))).thenReturn(jobItem);

        JobItem updatedItem = jobService.updateJobItem("employerId", "jobItemId", jobItem);

        assertThat(updatedItem).isNotNull();
        assertThat(updatedItem.getName()).isEqualTo("Updated Item");
    }

    @Test
    public void testUpdateJobItemNotFound() {
        Mockito.when(jobRepository.findByEmployerId(anyString())).thenReturn(new Job());
        Mockito.when(jobItemRepository.findById(anyString())).thenReturn(Optional.empty());

        JobItem updatedItem = new JobItem();
        assertThrows(EntityNotFoundException.class, () -> {
            jobService.updateJobItem("employerId", "jobItemId", updatedItem);
        });
    }

    @Test
    public void testGetJobByEmployerId() {
        Job job = new Job();
        Mockito.when(jobRepository.findByEmployerId(anyString())).thenReturn(job);

        Job foundJob = jobService.getJobByEmployerId("employerId");

        assertThat(foundJob).isNotNull();
    }

    @Test
    public void testGetJobByEmployerIdNotFound() {
        Mockito.when(jobRepository.findByEmployerId(anyString())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> {
            jobService.getJobByEmployerId("employerId");
        });
    }
}