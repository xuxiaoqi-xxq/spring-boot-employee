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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        Company savedCompany = companyRepository.save(company);
        Employee employee = new Employee(1, "eva", "male", 18, 1000, savedCompany.getCompanyId());
        employee.setCompanyId(savedCompany.getCompanyId());
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
        Company savedCompany = companyRepository.save(company);
        Employee employee1 = new Employee(1, "eva1", "male", 18, 1000, savedCompany.getCompanyId());
        Employee employee2 = new Employee(2, "eva2", "male", 18, 1000, savedCompany.getCompanyId());
        Employee employee3 = new Employee(3, "eva3", "male", 18, 1000, savedCompany.getCompanyId());
        employee1.setCompanyId(savedCompany.getCompanyId());
        employee2.setCompanyId(savedCompany.getCompanyId());
        employee3.setCompanyId(savedCompany.getCompanyId());
        List<Employee> savedEmployees = employeeRepository.saveAll(Arrays.asList(employee1, employee2, employee3));

        mockMvc.perform(get("/employees?page=2&pageSize=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].employeeId").isNumber())
                .andExpect(jsonPath("$.content[0].name").value(savedEmployees.get(2).getName()))
                .andExpect(jsonPath("$.content[0].age").value(savedEmployees.get(2).getAge()))
                .andExpect(jsonPath("$.content[0].salary").value(savedEmployees.get(2).getSalary()))
                .andExpect(jsonPath("$.content[0].gender").value(savedEmployees.get(2).getGender()));
    }

    @Test
    void should_gender_employees_when_hit_employees_endpoint_given_gender() throws Exception {
        //given
        Company company = new Company(1, "oocl", null);
        Company savedCompany = companyRepository.save(company);
        Employee employee1 = new Employee(1, "eva1", "male", 18, 1000, savedCompany.getCompanyId());
        Employee employee2 = new Employee(2, "eva2", "female", 18, 1000, savedCompany.getCompanyId());
        Employee employee3 = new Employee(3, "eva3", "male", 18, 1000, savedCompany.getCompanyId());
        employee1.setCompanyId(savedCompany.getCompanyId());
        employee2.setCompanyId(savedCompany.getCompanyId());
        employee3.setCompanyId(savedCompany.getCompanyId());
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

    @Test
    void should_specific_employee_when_hit_employees_endpoint_given_employee_id() throws Exception {
        //given
        Company company = new Company(1, "oocl", null);
        Company savedCompany = companyRepository.save(company);
        Employee employee1 = new Employee(1, "eva1", "male", 18, 1000, savedCompany.getCompanyId());
        Employee employee2 = new Employee(2, "eva2", "female", 18, 1000, savedCompany.getCompanyId());
        Employee employee3 = new Employee(3, "eva3", "male", 18, 1000, savedCompany.getCompanyId());
        employee1.setCompanyId(savedCompany.getCompanyId());
        employee2.setCompanyId(savedCompany.getCompanyId());
        employee3.setCompanyId(savedCompany.getCompanyId());
        List<Employee> savedEmployees = employeeRepository.saveAll(Arrays.asList(employee1, employee2, employee3));

        mockMvc.perform(get("/employees/" + savedEmployees.get(0).getEmployeeId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").isNumber())
                .andExpect(jsonPath("$.name").value(savedEmployees.get(0).getName()))
                .andExpect(jsonPath("$.age").value(savedEmployees.get(0).getAge()))
                .andExpect(jsonPath("$.salary").value(savedEmployees.get(0).getSalary()))
                .andExpect(jsonPath("$.gender").value(savedEmployees.get(0).getGender()));
    }

    @Test
    void should_add_employee_when_hit_employees_endpoint_given_employee() throws Exception {
        //given
        Company company = new Company(1, "oocl", null);
        companyRepository.save(company);

        String createEmployee = "{" +
                "    \"gender\":\"female\"," +
                "    \"name\":\"xxx\"," +
                "    \"age\":18," +
                "    \"salary\":10000," +
                "    \"companyId\":1," +
                "    \"employeeId\":1" +
                "}";

        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(createEmployee))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").isNumber())
                .andExpect(jsonPath("$.name").value("xxx"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.salary").value(10000))
                .andExpect(jsonPath("$.gender").value("female"))
                .andExpect(jsonPath("$.companyId").value(1));

        Employee employee = employeeRepository.findById(1).orElse(null);
        assertNotNull(employee);
        assertEquals("xxx", employee.getName());
        assertEquals(18, employee.getAge());
        assertEquals(10000, employee.getSalary());
        assertEquals("female", employee.getGender());
    }

    @Test
    void should_return_updated_employee_when_hit_employees_endpoint_given_new_employee() throws Exception {
        //given
        Company company = new Company(1, "oocl", null);
        Company savedCompany = companyRepository.save(company);
        Employee employee = new Employee(1, "myname", "female", 18, 10000, savedCompany.getCompanyId());
        employee.setCompanyId(savedCompany.getCompanyId());
        Employee savedEmployee = employeeRepository.save(employee);

        String updateEmployee = "{" +
                "    \"gender\":\"male\"," +
                "    \"name\":\"new name\"," +
                "    \"age\":20," +
                "    \"salary\":100000," +
                "    \"companyId\":" + savedCompany.getCompanyId() + "," +
                "    \"employeeId\":" + savedEmployee.getEmployeeId() +
                "}";

        mockMvc.perform(put("/employees/" + savedEmployee.getEmployeeId()).contentType(MediaType.APPLICATION_JSON).content(updateEmployee))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").isNumber())
                .andExpect(jsonPath("$.name").value("new name"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.salary").value(100000))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.companyId").value(1));

        Employee foundEmployee = employeeRepository.findById(savedEmployee.getEmployeeId()).orElse(null);
        assertNotNull(foundEmployee);
        assertEquals("new name", foundEmployee.getName());
        assertEquals(20, foundEmployee.getAge());
        assertEquals(100000, foundEmployee.getSalary());
        assertEquals("male", foundEmployee.getGender());
    }

    @Test
    void should_return_void_when_hit_employees_endpoint_given_employee_id() throws Exception {
        //given
        Company company = new Company(1, "oocl", null);
        Company savedCompany = companyRepository.save(company);
        Employee savedEmployee = employeeRepository.save(new Employee(1, "myname", "female", 18, 10000, savedCompany.getCompanyId()));

        mockMvc.perform(delete("/employees/" + savedEmployee.getEmployeeId()))
                .andExpect(status().isOk());

        assertNull(employeeRepository.findById(savedEmployee.getEmployeeId()).orElse(null));
    }
}
