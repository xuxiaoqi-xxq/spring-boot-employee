package com.oocl.controller;

import com.oocl.dto.RequestEmployee;
import com.oocl.dto.ResponseEmployee;
import com.oocl.entity.Employee;
import com.oocl.exception.IllegalOperationException;
import com.oocl.exception.NoSuchDataException;
import com.oocl.mapper.EmployeeMapper;
import com.oocl.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        List<Employee> employees = this.employeeService.findAll();
        return employees.stream().map(employeeMapper::to).collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public Page<ResponseEmployee> getAllEmployeesByPageAndSize(Integer page, Integer pageSize) {
        Page<Employee> employees =  this.employeeService.findAllByPageAndPageSize(--page, pageSize);
        return employees.map(employeeMapper::to);
    }

    @GetMapping(params = {"gender"})
    public List<ResponseEmployee> getAllEmployeesByGender(String gender) {
        List<Employee> employees = this.employeeService.findAllByGender(gender);
        return employees.stream().map(employeeMapper::to).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEmployee getEmployee(@PathVariable("id") Integer employeeId) throws NoSuchDataException {
        Employee employee = this.employeeService.findById(employeeId);
        return employeeMapper.to(employee);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEmployee addEmployee(@RequestBody RequestEmployee requestEmployee) throws IllegalOperationException {
        Employee employee = this.employeeService.add(employeeMapper.from(requestEmployee));
        return employeeMapper.to(employee);
    }

    @PutMapping("/{id}")
    public ResponseEmployee updateEmployee(@RequestBody RequestEmployee requestEmployee, @PathVariable("id") Integer id) throws IllegalOperationException, NoSuchDataException {
        Employee employee = this.employeeService.update(id, employeeMapper.from(requestEmployee));
        return employeeMapper.to(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id") Integer employeeId) {
        this.employeeService.deleteByID(employeeId);
    }
}
