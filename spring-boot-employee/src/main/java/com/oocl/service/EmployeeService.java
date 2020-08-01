package com.oocl.service;

import com.oocl.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {
    public List<Employee> findAll() {
        return Arrays.asList(new Employee());
    }
}
