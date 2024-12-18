package com.bits.job.portal.repository;

import com.bits.job.portal.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindByUsername() {
        // Given
        Employee employee = new Employee();
        employee.setId("1");
        employee.setUsername("testuser");
        employeeRepository.save(employee);

        // When
        Optional<Employee> foundEmployee = employeeRepository.findByUsername("testuser");

        // Then
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getUsername()).isEqualTo("testuser");
    }
}