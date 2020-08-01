package com.oocl.service;

import com.oocl.dto.ResponseEmployee;
import com.oocl.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {
    public List<ResponseEmployee> findAll() {
        return Arrays.asList(new ResponseEmployee());
    }

    public Page<ResponseEmployee> findAllByPageAndPageSize(Integer integer, Integer pageSize) {
        return new PageImpl<>(Arrays.asList(new ResponseEmployee()));
    }

    public List<ResponseEmployee> findAllByGender(String gender) {
        return null;
    }

    public ResponseEmployee findById(Integer employeeId) {
        return null;
    }

    public ResponseEmployee add(Employee employee) {
        return null;
    }

    public ResponseEmployee update(Integer employeeId, Employee newEmployee) {
        return null;
    }

    public void deleteByID(Integer employeeId) {
    }
}
