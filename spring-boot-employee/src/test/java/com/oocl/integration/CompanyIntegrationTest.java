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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }

    @Test
    void should_return_companies_when_hit_companies_endpoint_given_nothing() throws Exception {
        //given
        Company company = new Company(1, "oocl", null);
        companyRepository.save(company);

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].companyId").isNumber())
                .andExpect(jsonPath("$[0].name").value("oocl"));
    }

    @Test
    void should_return_specific_company_when_hit_companies_endpoint_given_company_id() throws Exception {
        //given
        Company company = new Company(1, "oocl", null);
        Company savedCompany = companyRepository.save(company);

        mockMvc.perform(get("/companies/" + savedCompany.getCompanyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").isNumber())
                .andExpect(jsonPath("$.name").value("oocl"));
    }

    @Test
    void should_return_employee_when_hit_companies_endpoint_given_company_id() throws Exception {
        //given
        Company company = new Company(1, "oocw", null);
        Company savedCompany = companyRepository.save(company);
        List<Employee> employees = Arrays.asList(new Employee(1, "eva1", "female", 18, 1000),
                new Employee(2, "eva2", "female", 19, 10000));

        for (Employee employee: employees){
            employee.setCompany(company);
        }
        employeeRepository.saveAll(employees);

        mockMvc.perform(get("/companies/" + company.getCompanyId() + "/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("eva1"));
    }
}
