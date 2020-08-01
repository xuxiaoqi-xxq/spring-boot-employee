package com.oocl.service;

import com.oocl.dto.ResponseEmployee;
import com.oocl.entity.Employee;
import com.oocl.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return null;
    }

    public Page<Employee> findAllByPageAndPageSize(Integer integer, Integer pageSize) {
        return null;
    }

    public List<Employee> findAllByGender(String gender) {
        return null;
    }

    public Employee findById(Integer employeeId) {
        return null;
    }

    public Employee add(Employee employee) {
        return null;
    }

    public Employee update(Integer employeeId, Employee newEmployee) {
        return null;
    }

    public void deleteByID(Integer employeeId) {
    }
}
