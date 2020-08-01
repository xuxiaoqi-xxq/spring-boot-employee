package com.oocl.service;

import com.oocl.entity.Employee;
import com.oocl.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

public class EmployeeServiceTest {

    @Test
    void should_get_all_employees_when_get_given() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> employees = Collections.singletonList(new Employee(1, "eva", "female", 18, 10000));
        given(employeeRepository.findAll()).willReturn(employees);

        //when
        List<Employee> foundEmployees = employeeService.findAll();

        //then
        assertEquals(employees, foundEmployees);
    }

    @Test
    void should_get_page_employees_when_get_by_page_given_page_pageSize() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Page<Employee> employees = new PageImpl<>(Arrays.asList(new Employee(1, "eva", "female", 18, 1000),
                new Employee(2, "vae", "male", 20, 1000)));
        given(employeeRepository.findAll(PageRequest.of(1, 2))).willReturn(employees);

        //when
        Page<Employee> employeesByPageAndPageSize = employeeService.findAllByPageAndPageSize(1, 2);

        //then
        assertEquals(employees, employeesByPageAndPageSize);
    }
}
