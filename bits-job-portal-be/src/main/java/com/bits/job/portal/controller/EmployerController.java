package com.bits.job.portal.controller;


import com.bits.job.portal.enums.JobItemType;
import com.bits.job.portal.model.Employer;
import com.bits.job.portal.service.EmployerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employer")
@RequiredArgsConstructor
public class EmployerController {
    private final EmployerService employerService;

    @PostMapping("/register")
    public ResponseEntity<Employer> registerEmployer(@RequestBody Employer employer) {
        Employer registeredEmployer = employerService.registerEmployer(employer);
        return ResponseEntity.ok(registeredEmployer);
    }

    @GetMapping("/getallemployer")
    public ResponseEntity<List<Employer>> getAllEmployers() {
        List<Employer> employers = employerService.getAllEmployers();
        return ResponseEntity.ok(employers);
    }

    @PutMapping("/{employerId}")
    public ResponseEntity<Employer> updateEmployer(@PathVariable String employerId, @Valid @RequestBody Employer updatedEmployer) {
        Employer updated = employerService.updateEmployer(employerId, updatedEmployer);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employer>> searchEmployer(
            @RequestParam JobItemType type,
            @RequestParam String name) {
        List<Employer> employers = employerService.searchEmployers(type, name);
        return ResponseEntity.ok(employers);
    }
}