package com.bits.job.portal.service;

import com.bits.job.portal.enums.ROLE;
import com.bits.job.portal.exception.EntityAlreadyExistException;
import com.bits.job.portal.exception.EntityNotFoundException;
import com.bits.job.portal.model.Employee;
import com.bits.job.portal.model.User;
import com.bits.job.portal.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserService userService;

    public Employee registerEmployee(Employee employee) {
        employeeRepository.findByUsername(employee.getUsername()).ifPresent(existingEmployee -> {
            throw new EntityAlreadyExistException(
                    "Employee with username " + employee.getUsername() + " already exists");
        });

        User employeeUser = User.builder().firstName(employee.getName())
                .username(employee.getUsername()).password(employee.getPassword())
                .roles(Set.of(ROLE.EMPLOYEE.getRole())).build();
        userService.saveUser(employeeUser);

        employee.setUserId(employeeUser.getId());

        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee) {

        Employee employeeUser = employeeRepository.findById(employee.getId()).orElseThrow(() ->
                new EntityNotFoundException("Employee not found"));
        employeeUser.setEmailAddress(employee.getEmailAddress());
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployee(String id) {
        return employeeRepository.findById(id).get();
    }
}