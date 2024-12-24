package com.bits.job.portal.controller;

import com.bits.job.portal.model.Candidate;
import com.bits.job.portal.model.Employer;
import com.bits.job.portal.model.Job;
import com.bits.job.portal.repository.EmployeeRepository;
import com.bits.job.portal.repository.EmployerRepository;
import com.bits.job.portal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Add an employer to the database
    @PostMapping("/employer/add")
    public Employer addEmployer(@RequestBody Employer employer) {
        return employerRepository.save(employer);
    }

    // Add a job and associate it with an employer
    @PostMapping("/add")
    public Job addJob(@RequestBody Job job) {
        // Get the employer from the database
        Employer employer = employerRepository.findByName(job.getEmployer().getName());
        if (employer != null) {
            job.setEmployer(employer);
            // Save the job and add it to the employer's job list
            employer.getJobs().add(job);
            employerRepository.save(employer);
            return jobRepository.save(job);
        } else {
            throw new RuntimeException("Employer not found");
        }
    }

    @PutMapping("/employee/{jobTitle}/apply")
    public Job linkEmployeeToJob(@PathVariable String jobTitle, @RequestBody Candidate employee) {
        Job job = jobRepository.findByTitle(jobTitle);
        if (job != null) {
            job.setEmployee(employee); // Set the employee to the job
            jobRepository.save(job); // Save the updated job with the employee relationship
            return job;
        } else {
            throw new RuntimeException("Job not found");
        }
}


    // Add an employee to the database
    @PostMapping("/employee/add")
    public Candidate addEmployee(@RequestBody Candidate employee) {
        return employeeRepository.save(employee);
    }

    // Get job by title
    @GetMapping("/title/{title}")
    public Job getJob(@PathVariable String title) {
        return jobRepository.findByTitle(title);
    }

    // Get employer by name
    @GetMapping("/employer/{name}")
    public Employer getEmployer(@PathVariable String name) {
        return employerRepository.findByName(name);
    }

    // Get employee by name
    @GetMapping("/employee/{name}")
    public Candidate getEmployee(@PathVariable String name) {
        return employeeRepository.findByName(name);
    }
}
