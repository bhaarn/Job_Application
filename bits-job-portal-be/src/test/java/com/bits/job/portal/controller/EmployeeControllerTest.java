package com.bits.job.portal.controller;

import com.bits.job.portal.controller.EmployeeController;
import com.bits.job.portal.model.Employee;
import com.bits.job.portal.model.Application;
import com.bits.job.portal.service.CustomUserDetailsService;
import com.bits.job.portal.service.EmployeeService;
import com.bits.job.portal.service.JwtService;
import com.bits.job.portal.service.ApplicationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private ApplicationService applicationService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testRegisterEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setName("John Doe");

        Mockito.when(employeeService.registerEmployee(any(Employee.class))).thenReturn(employee);
        Mockito.when(jwtService.generateToken(any())).thenReturn("valid-token");
        Mockito.when(customUserDetailsService.loadUserByUsername(anyString())).thenReturn(new org.springframework.security.core.userdetails.User("user", "password", new ArrayList<>()));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\", \"password\": \"password123\"}")
                        .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andDo(print());
    }

    @Test
    public void testGetApplicationHistory() throws Exception {
        Application application = new Application();
        application.setId("1");
        application.setEmployeeId("1");
        List<Application> applications = Collections.singletonList(application);

        Mockito.when(applicationService.getApplicationHistory(anyString())).thenReturn(applications);
        Mockito.when(customUserDetailsService.loadUserByUsername(anyString())).thenReturn(new org.springframework.security.core.userdetails.User("user", "password", new ArrayList<>()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/employee/1/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].employeeId").value("1"))
                .andDo(print());
    }
}