package com.oocl.controller;

import com.oocl.entity.Employee;
import com.oocl.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public List<Employee> getAllEmployees() {
        return this.employeeService.findAll();
    }

    @GetMapping(params = {"page", "pageSize"})
    public Page<Employee> getAllEmployeesByPageAndSize(Integer page, Integer pageSize) {
        return this.employeeService.findAllByPageAndPageSize(--page, pageSize);
    }
}
