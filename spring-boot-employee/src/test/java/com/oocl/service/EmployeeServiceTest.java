package com.oocl.service;

import com.oocl.entity.Employee;
import com.oocl.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
}
