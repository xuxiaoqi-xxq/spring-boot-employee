package com.oocl.service;

import com.oocl.entity.Company;
import com.oocl.entity.Employee;
import com.oocl.repository.CompanyRepository;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CompanyServiceTest {

    @Test
    void should_return_all_companies_when_find_all_given() {
        //given
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository, null);
        List<Company> companies = Collections.singletonList(new Company(1, "OOCL", null));
        given(companyRepository.findAll()).willReturn(companies);

        //when
        List<Company> allCompanies = companyService.findAll();

        //then
        assertEquals(companies, allCompanies);
    }

    @Test
    void should_return_specific_company_when_find_by_id_given_id() {
        //given
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository, null);
        Company ooclCompany = new Company(1, "OOCL", null);
        given(companyRepository.findById(1)).willReturn(Optional.of(ooclCompany));

        //when
        Company company = companyService.findByID(1);

        //then
        assertEquals(ooclCompany, company);
    }

    @Test
    void should_return_employees_when_find_employees_given_company_id() {
        //given
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository, null);
        List<Employee> employees = Collections.singletonList(new Employee(1, "eva", "female", 18, 10000));
        Company company = new Company(1, "OOCL", employees);
        given(companyRepository.findById(1)).willReturn(Optional.of(company));

        //when
        List<Employee> employeesByCompanyID = companyService.findEmployeesByCompanyID(1);

        //then
        assertEquals(employees, employeesByCompanyID);
    }

}
