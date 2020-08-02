package com.oocl.exception;

import com.oocl.entity.Employee;
import com.oocl.repository.EmployeeRepository;
import com.oocl.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

public class NoSuchDataExceptionTest {

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
        Employee updatedEmployee = new Employee(1, "eva", "female", 18, 1000);

        //then
        assertThrows(IllegalOperationException.class, () -> employeeService.update(2, updatedEmployee));
    }
}
