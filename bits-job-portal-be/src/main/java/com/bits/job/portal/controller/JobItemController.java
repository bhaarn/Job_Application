package com.bits.job.portal.controller;

import com.bits.job.portal.model.Job;
import com.bits.job.portal.model.JobItem;
import com.bits.job.portal.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employer")
@RequiredArgsConstructor
@Validated
public class JobItemController {
    private final JobService jobService;

    @PostMapping("/{employerId}/job/item")
    public ResponseEntity<JobItem> createJobItem(@PathVariable String employerId, @Valid @RequestBody JobItem jobItem) {
        JobItem createdJobItem = jobService.createJobItem(employerId, jobItem);
        return ResponseEntity.ok(createdJobItem);
    }

    @DeleteMapping("/{employerId}/job/item/{jobItemId}")
    public ResponseEntity<Void> deleteJobItem(@PathVariable String employerId, @PathVariable String jobItemId) {
        jobService.deleteJobItem(employerId, jobItemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{employerId}/job")
    public ResponseEntity<Job> getJobByEmployerId(@PathVariable String employerId) {
        Job job = jobService.getJobByEmployerId(employerId);
        return ResponseEntity.ok(job);
    }

    @PutMapping("/{employerId}/job/item/{jobItemId}")
    public ResponseEntity<JobItem> updateJobItem(@PathVariable String employerId, @PathVariable String jobItemId,
                                                   @Valid @RequestBody JobItem updatedItem) {
        JobItem updatedJobItem = jobService.updateJobItem(employerId, jobItemId, updatedItem);
        return ResponseEntity.ok(updatedJobItem);
    }
}