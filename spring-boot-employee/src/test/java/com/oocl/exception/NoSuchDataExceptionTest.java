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
        Exception exception = assertThrows(NoSuchDataException.class, () -> employeeService.findById(1));
        assertEquals("no such data", exception.getMessage());
    }
}
