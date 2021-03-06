package com.oocl.service;

import com.oocl.entity.Company;
import com.oocl.entity.Employee;
import com.oocl.exception.IllegalOperationException;
import com.oocl.exception.NoSuchDataException;
import com.oocl.repository.CompanyRepository;
import com.oocl.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    void should_return_specific_company_when_find_by_id_given_id() throws NoSuchDataException, IllegalOperationException {
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
        List<Employee> employees = Collections.singletonList(new Employee(1, "eva", "female", 18, 10000,1));
        Company company = new Company(1, "OOCL", employees);
        given(companyRepository.findById(1)).willReturn(Optional.of(company));

        //when
        List<Employee> employeesByCompanyID = companyService.findEmployeesByCompanyId(1);

        //then
        assertEquals(employees, employeesByCompanyID);
    }

    @Test
    void should_get_page_companies_when_get_by_page_given_page_and_size() {
        //given
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository, null);
        Page<Company> companies = new PageImpl<>(Arrays.asList(new Company(1, "OOCL", null), new Company(2, "OOCL", null)));
        given(companyRepository.findAll(PageRequest.of(1, 2))).willReturn(companies);

        //when
        Page<Company> companiesByPageAndPageSize = companyService.findAllByPageAndPageSize(1, 2);

        //then
        assertEquals(companies, companiesByPageAndPageSize);
    }

    @Test
    void should_return_company_when_add_given_company() throws IllegalOperationException {
        //given
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository, null);
        Company company = new Company(1, "OOCL", null);
        given(companyRepository.save(company)).willReturn(company);

        //when
        Company createdCompany = companyService.add(company);

        //then
        assertEquals(company, createdCompany);
    }

    @Test
    void should_return_company_when_update_given_company() throws IllegalOperationException, NoSuchDataException {
        //given
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository, null);
        given(companyRepository.findById(1)).willReturn(Optional.of(new Company(1, "OOCL", null)));
        Company company = new Company(1, "CargoSmart", null);
        when(companyRepository.save(any())).thenReturn(company);

        //when
        Company updatedCompany = companyService.update(1, company);

        //then
        assertEquals(1, updatedCompany.getCompanyId());
        assertEquals("CargoSmart", updatedCompany.getName());
        assertNull(updatedCompany.getEmployees());
    }

    @Test
    void should_return_void_when_delete_given_company_id() throws IllegalOperationException {
        //given
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        Optional<Company> specificCompany = Optional.of(new Company(1, "name", asList(new Employee(1, "eva", "female", 19, 2000,1))));
        when(companyRepository.findById(1)).thenReturn(specificCompany);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);

        CompanyService companyService = new CompanyService(companyRepository, employeeRepository);

        //when
        companyService.deleteById(1);

        //then
        verify(companyRepository).deleteById(1);
    }

    @Test
    void should_throw_NoSuchDataException_when_find_by_id_given_wrong_id() {
        //given
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository, null);
        given(companyRepository.findById(1)).willReturn(Optional.empty());

        //then
        assertThrows(NoSuchDataException.class, () -> companyService.findByID(1));
    }

    @Test
    void should_throw_IllegalOperationException_when_find_by_id_given_null_id() {
        //given
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository, null);

        //then
        assertThrows(IllegalOperationException.class, () -> companyService.findByID(null));
    }

    @Test
    void should_throw_IllegalOperationException_when_add_given_null() {
        //given
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository, null);

        //then
        assertThrows(IllegalOperationException.class, () -> companyService.add(null));
    }

    @Test
    void should_throw_IllegalOperationException_when_update_given_diff_id() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository, null);

        //when
        Company updatedCompany = new Company(1, "eva", null);

        //then
        assertThrows(IllegalOperationException.class, () -> companyService.update(2, updatedCompany));
    }

    @Test
    void should_throw_NoSuchDataException_when_update_given_wrong_id() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository, null);
        when(companyRepository.findById(1)).thenReturn(Optional.empty());

        //when
        Company updatedCompany = new Company(2, "eva", null);

        //then
        assertThrows(NoSuchDataException.class, () -> companyService.update(2, updatedCompany));
    }

    @Test
    void should_throw_IllegalOperationException_when_delete_given_null() {
        //given
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository, null);

        //then
        assertThrows(IllegalOperationException.class, () -> companyService.deleteById(null));
    }

}
