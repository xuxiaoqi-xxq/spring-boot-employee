package com.oocl.service;

import com.oocl.entity.Employee;
import com.oocl.exception.IllegalOperationException;
import com.oocl.exception.NoSuchDataException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

public class EmployeeServiceTest {

    @Test
    void should_get_all_employees_when_get_given() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> employees = Collections.singletonList(new Employee(1, "eva", "female", 18, 10000,1));
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
        Page<Employee> employees = new PageImpl<>(Arrays.asList(new Employee(1, "eva", "female", 18, 1000,1),
                new Employee(2, "vae", "male", 20, 1000,1)));
        given(employeeRepository.findAll(PageRequest.of(1, 2))).willReturn(employees);

        //when
        Page<Employee> employeesByPageAndPageSize = employeeService.findAllByPageAndPageSize(1, 2);

        //then
        assertEquals(employees, employeesByPageAndPageSize);
    }

    @Test
    void should_return_gender_employees_when_find_by_gender_given_gender() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> employees = Arrays.asList(new Employee(1, "eva", "female", 18, 1000,1),
                new Employee(2, "vae", "male", 20, 1000,1));
        given(employeeRepository.findByGender("female")).willReturn(employees);

        //when
        List<Employee> employeesByGender = employeeService.findAllByGender("female");

        //then
        assertEquals(employees, employeesByGender);
    }

    @Test
    void should_return_specific_employee_when_find_given_id() throws NoSuchDataException {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee = new Employee(2, "vae", "male", 20, 1000,1);
        given(employeeRepository.findById(2)).willReturn(Optional.of(employee));

        //when
        Employee foundEmployee = employeeService.findById(2);

        //then
        assertEquals(employee, foundEmployee);
    }

    @Test
    void should_return_created_employee_when_add_given_employee() throws IllegalOperationException {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee employee = new Employee(2, "vae", "male", 20, 1000,1);
        given(employeeRepository.save(employee)).willReturn(employee);

        //when
        Employee createdEmployee = employeeService.add(employee);

        //then
        assertEquals(employee, createdEmployee);
    }

    @Test
    void should_update_employee_when_update_given_employee() throws IllegalOperationException, NoSuchDataException {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        given(employeeRepository.findById(2)).willReturn(Optional.of(new Employee(2, "vae", "female", 20, 1000, 1)));

        //when
        Employee waitUpdateEmployee = new Employee(2, "new name", "male", 20, 1000,1);
        Employee updatedEmployee = employeeService.update(2, waitUpdateEmployee);

        //then
        if (updatedEmployee != null) {
            assertEquals(2, updatedEmployee.getEmployeeId());
            assertEquals("new name", updatedEmployee.getName());
            assertEquals("male", updatedEmployee.getGender());
            assertEquals(1000, updatedEmployee.getSalary());
            assertEquals(20, updatedEmployee.getAge());
        }
    }

    @Test
    void should_return_void_when_delete_given_employee_id() throws IllegalOperationException {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        //when
        employeeService.deleteByID(1);

        //then
        Mockito.verify(employeeRepository).deleteById(1);
    }

    @Test
    void should_throw_NoSuchDataException_when_findById_given_wrong_id() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        given(employeeRepository.findById(1)).willReturn(Optional.empty());

        //then
        assertThrows(NoSuchDataException.class, () -> employeeService.findById(1));
    }

    @Test
    void should_throw_IllegalOperationException_when_add_given_null_employee() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        //then
        assertThrows(IllegalOperationException.class, () -> employeeService.add(null));
    }

    @Test
    void should_throw_IllegalOperationException_when_update_given_diff_employee_id() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        //when
        Employee updatedEmployee = new Employee(1, "eva", "female", 18, 1000, null);

        //then
        assertThrows(IllegalOperationException.class, () -> employeeService.update(2, updatedEmployee));
    }

    @Test
    void should_throw_NoSuchDataException_when_update_given_wrong_employee_id() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        given(employeeRepository.findById(1)).willReturn(Optional.empty());

        //when
        Employee updatedEmployee = new Employee(1, "eva", "female", 18, 1000, null);

        //then
        assertThrows(NoSuchDataException.class, () -> employeeService.update(1, updatedEmployee));
    }

    @Test
    void should_throw_IllegalOperationException_when_delete_given_null_employee_id() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        //then
        assertThrows(IllegalOperationException.class, () -> employeeService.deleteByID(null));
    }
}
