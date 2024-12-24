package com.bits.job.portal.controller;


import com.bits.job.portal.model.Employee;
import com.bits.job.portal.model.Application;
import com.bits.job.portal.service.EmployeeService;
import com.bits.job.portal.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@Validated
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ApplicationService applicationService;

    @PostMapping("/register")
    public ResponseEntity<Employee> registerEmployee(@Valid @RequestBody Employee employee) {
        Employee registeredEmployee = employeeService.registerEmployee(employee);
        return ResponseEntity.ok(registeredEmployee);
    }

    @GetMapping("/{employeeId}/applications")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<Application>> getApplicationHistory(@PathVariable String employeeId) {
        List<Application> applicationHistory = applicationService.getApplicationHistory(employeeId);
        return ResponseEntity.ok(applicationHistory);
    }

    @GetMapping("/getallemployee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/getemployee/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable String employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return ResponseEntity.ok(updatedEmployee);
    }
}
