package com.oocl.controller;

import com.oocl.dto.RequestEmployee;
import com.oocl.dto.ResponseEmployee;
import com.oocl.entity.Employee;
import com.oocl.mapper.EmployeeMapper;
import com.oocl.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.management.MemoryUsage;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;


    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping()
    public List<ResponseEmployee> getAllEmployees() {
        return this.employeeService.findAll();
    }

    @GetMapping(params = {"page", "pageSize"})
    public Page<ResponseEmployee> getAllEmployeesByPageAndSize(Integer page, Integer pageSize) {
        return this.employeeService.findAllByPageAndPageSize(--page, pageSize);
    }

    @GetMapping(params = {"gender"})
    public List<ResponseEmployee> getAllEmployeesByGender(String gender) {
        return this.employeeService.findAllByGender(gender);
    }

    @GetMapping("/{id}")
    public ResponseEmployee getEmployee(@PathVariable("id") Integer employeeId) {
        return this.employeeService.findById(employeeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEmployee addEmployee(@RequestBody RequestEmployee requestEmployee) {
        return this.employeeService.add(employeeMapper.from(requestEmployee));
    }

    @PutMapping("/{id}")
    public ResponseEmployee updateEmployee(@RequestBody RequestEmployee requestEmployee, @PathVariable("id") Integer id) {
        return this.employeeService.update(id, employeeMapper.from(requestEmployee));
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id") Integer employeeId) {
        this.employeeService.deleteByID(employeeId);
    }
}
