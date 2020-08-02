package com.oocl.service;

import com.oocl.entity.Employee;
import com.oocl.exception.IllegalOperationException;
import com.oocl.exception.NoSuchDataException;
import com.oocl.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    public Employee findById(Integer employeeId) throws NoSuchDataException {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if(employee == null) {
            throw new NoSuchDataException();
        }
        return employee;
    }

    public Employee add(Employee employee) throws IllegalOperationException {
        if (employee == null || employee.getEmployeeId() == null){
            throw new IllegalOperationException();
        }
        return employeeRepository.save(employee);
    }

    public Employee update(Integer employeeId, Employee newEmployee) {
        Employee foundEmployee = employeeRepository.findById(employeeId).orElse(null);
        if(foundEmployee != null && employeeId.equals(newEmployee.getEmployeeId())) {
            BeanUtils.copyProperties(newEmployee, foundEmployee);
        }
        return foundEmployee;
    }

    public void deleteByID(Integer employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
