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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
            employee.setCompany(savedCompany);
        }
        employeeRepository.saveAll(employees);

        mockMvc.perform(get("/companies/" + savedCompany.getCompanyId() + "/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("eva1"));

        employeeRepository.deleteAll();
    }

    @Test
    void should_page_companies_when_hit_companies_endpoint_given_page_and_pageSize() throws Exception {
        //given
        Company company1 = new Company(1, "oocl1", null);
        Company company2 = new Company(2, "oocl2", null);
        Company company3 = new Company(3, "oocl3", null);
        List<Company> companies = Arrays.asList(company1, company2, company3);
        companyRepository.saveAll(companies);

        mockMvc.perform(get("/companies?page=2&pageSize=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].companyId").isNumber())
                .andExpect(jsonPath("$.content[0].name").value("oocl2"));
    }

    @Test
    void should_return_created_companies_when_hit_companies_endpoint_given_company() throws Exception {
        //given
        String createdCompany = "{ " +
                "    \"companyId\": 1," +
                "    \"name\": \"gdsa\"," +
                "    \"employees\": null" +
                "}";

        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(createdCompany))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyId").isNumber())
                .andExpect(jsonPath("$.name").value("gdsa"));

        List<Company> companies = companyRepository.findAll();
        assertEquals("gdsa", companies.get(0).getName());
    }

    @Test
    void should_return_updated_companies_when_hit_companies_endpoint_given_new_company() throws Exception {
        //given
        Company company = new Company(1, "oocw", null);
        Company savedCompany = companyRepository.save(company);
        String createdCompany = "{ " +
                "    \"companyId\": "+ savedCompany.getCompanyId() + "," +
                "    \"name\": \"gdsa\"," +
                "    \"employees\": null" +
                "}";
        //when
        mockMvc.perform(put("/companies/" + savedCompany.getCompanyId()).contentType(MediaType.APPLICATION_JSON).content(createdCompany))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").isNumber())
                .andExpect(jsonPath("$.name").value("gdsa"));
        //then
        List<Company> companies = companyRepository.findAll();
        assertEquals("gdsa", companies.get(0).getName());
    }

    @Test
    void should_return_void_when_hit_companies_endpoint_given_company_id() throws Exception {
        //given
        Company company = new Company(1, "oocw", null);
        Company savedCompany = companyRepository.save(company);
        Employee employee = new Employee(1, "eva", "female", 19, 10000);
        employee.setCompany(savedCompany);
        Employee savedEmployee = employeeRepository.save(employee);

        mockMvc.perform(delete("/companies/" + savedCompany.getCompanyId()))
                .andExpect(status().isOk());

        assertNull(companyRepository.findById(savedCompany.getCompanyId()).orElse(null));
        assertNull(employeeRepository.findById(savedEmployee.getEmployeeId()).orElse(null));
    }
}
