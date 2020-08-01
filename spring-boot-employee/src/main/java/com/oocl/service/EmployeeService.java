package com.oocl.service;

import com.oocl.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {
    public List<Employee> findAll() {
        return Arrays.asList(new Employee());
    }

    public Page<Employee> findAllByPageAndPageSize(Integer integer, Integer pageSize) {
        return new PageImpl<>(Arrays.asList(new Employee()));
    }
}
