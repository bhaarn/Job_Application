package com.bits.job.portal.service;


import com.bits.job.portal.enums.JobItemType;
import com.bits.job.portal.enums.ROLE;
import com.bits.job.portal.exception.EntityAlreadyExistException;
import com.bits.job.portal.exception.EntityNotFoundException;
import com.bits.job.portal.model.Employer;
import com.bits.job.portal.model.User;
import com.bits.job.portal.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployerService {
    private final EmployerRepository employerRepository;
    private final UserService userService;
    private final JobService jobService;

    @Transactional
    public Employer registerEmployer(Employer employer) {
        employerRepository.findByName(employer.getName()).ifPresent(existingEmployer -> {
            throw new EntityAlreadyExistException("Employer with name " + employer.getName() + " already exists");
        });

        User employerUser = User.builder().firstName(employer.getName())
                .username(employer.getUsername()).password(employer.getPassword())
                .roles(Set.of(ROLE.EMPLOYER.getRole())).build();
        userService.saveUser(employerUser);

        employer.setUserId(employerUser.getId());
        employer.setJob(jobService.createJob(employer.getJob()));
        return employerRepository.save(employer);
    }

    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    public Employer updateEmployer(String employerId, Employer updatedEmployer) {
        Employer employer = employerRepository.findById(employerId).orElseThrow(() ->
                new EntityNotFoundException("Employer with ID " + employerId + " not found"));

        employer.setName(updatedEmployer.getName());
        employer.setEmailAddress(updatedEmployer.getEmailAddress());
        employer.setPhoneNumber(updatedEmployer.getPhoneNumber());

        return employerRepository.save(employer);
    }

    public List<Employer> searchEmployers(JobItemType type, String name) {
        return employerRepository.findByJobItemTypeAndName(type, name);
    }
}