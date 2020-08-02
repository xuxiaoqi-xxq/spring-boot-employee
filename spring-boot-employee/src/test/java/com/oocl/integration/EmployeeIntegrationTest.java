package com.oocl.integration;

import com.oocl.entity.Company;
import com.oocl.entity.Employee;
import com.oocl.repository.CompanyRepository;
import com.oocl.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void should_return_all_employees_when_hit_employees_endpoint_given_nothing() throws Exception {
        //given
        Company company = new Company(1, "oocl", null);
        companyRepository.save(company);
        Employee employee = new Employee(1, "eva", "male", 18, 1000);
        employee.setCompany(company);
        employeeRepository.save(employee);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].employeeId").isNumber())
                .andExpect(jsonPath("$[0].name").value(employee.getName()))
                .andExpect(jsonPath("$[0].age").value(employee.getAge()))
                .andExpect(jsonPath("$[0].salary").value(employee.getSalary()))
                .andExpect(jsonPath("$[0].gender").value(employee.getGender()));
    }

    @Test
    void should_page_employees_when_hit_employees_endpoint_given_page_and_pageSize() throws Exception {
        //given
        Company company = new Company(1, "oocl", null);
        companyRepository.save(company);
        Employee employee1 = new Employee(1, "eva1", "male", 18, 1000);
        Employee employee2 = new Employee(2, "eva2", "male", 18, 1000);
        Employee employee3 = new Employee(3, "eva3", "male", 18, 1000);
        employee1.setCompany(company);
        employee2.setCompany(company);
        employee3.setCompany(company);
        employeeRepository.saveAll(Arrays.asList(employee1, employee2, employee3));

        mockMvc.perform(get("/employees?page=2&pageSize=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].employeeId").isNumber())
                .andExpect(jsonPath("$.content[0].name").value(employee2.getName()))
                .andExpect(jsonPath("$.content[0].age").value(employee2.getAge()))
                .andExpect(jsonPath("$.content[0].salary").value(employee2.getSalary()))
                .andExpect(jsonPath("$.content[0].gender").value(employee2.getGender()));
    }

    @Test
    void should_gender_employees_when_hit_employees_endpoint_given_gender() throws Exception {
        //given
        Company company = new Company(1, "oocl", null);
        companyRepository.save(company);
        Employee employee1 = new Employee(1, "eva1", "male", 18, 1000);
        Employee employee2 = new Employee(2, "eva2", "female", 18, 1000);
        Employee employee3 = new Employee(3, "eva3", "male", 18, 1000);
        employee1.setCompany(company);
        employee2.setCompany(company);
        employee3.setCompany(company);
        employeeRepository.saveAll(Arrays.asList(employee1, employee2, employee3));

        mockMvc.perform(get("/employees?gender=female"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].employeeId").isNumber())
                .andExpect(jsonPath("$[0].name").value(employee2.getName()))
                .andExpect(jsonPath("$[0].age").value(employee2.getAge()))
                .andExpect(jsonPath("$[0].salary").value(employee2.getSalary()))
                .andExpect(jsonPath("$[0].gender").value(employee2.getGender()));
    }
}
