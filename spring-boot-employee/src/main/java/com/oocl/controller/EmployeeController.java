package com.oocl.controller;

import com.oocl.dto.RequestEmployee;
import com.oocl.dto.ResponseEmployee;
import com.oocl.entity.Employee;
import com.oocl.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
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
        return this.employeeService.add(requestEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEmployee updateEmployee(@RequestBody RequestEmployee newEmployee, @PathVariable("id") Integer id) {
        return this.employeeService.update(id, newEmployee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id") Integer employeeId) {
        this.employeeService.deleteByID(employeeId);
    }
}
