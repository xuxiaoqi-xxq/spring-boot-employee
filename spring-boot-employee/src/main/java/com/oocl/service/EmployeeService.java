package com.oocl.service;

import com.oocl.dto.ResponseEmployee;
import com.oocl.entity.Employee;
import com.oocl.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
        return employeeRepository.findAll();
    }

    public Page<Employee> findAllByPageAndPageSize(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize));
    }

    public List<Employee> findAllByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Employee findById(Integer employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public Employee add(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(Integer employeeId, Employee newEmployee) {
        Employee foundEmployee = employeeRepository.findById(employeeId).orElse(null);
        if(foundEmployee != null && employeeId == newEmployee.getEmployeeId()) {
            BeanUtils.copyProperties(newEmployee, foundEmployee);
        }
        return foundEmployee;
    }

    public void deleteByID(Integer employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
