package com.oocl.controller;

import com.oocl.dto.RequestCompany;
import com.oocl.dto.ResponseCompany;
import com.oocl.dto.ResponseEmployee;
import com.oocl.entity.Company;
import com.oocl.entity.Employee;
import com.oocl.mapper.CompanyMapper;
import com.oocl.mapper.EmployeeMapper;
import com.oocl.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    private final CompanyMapper companyMapper;

    public CompanyController(CompanyService companyService, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @GetMapping()
    List<ResponseCompany> getCompanies() {
        return this.companyService.findAll();
    }

    @GetMapping("/{id}")
    ResponseCompany getCompanyById(@PathVariable("id") Integer companyId) {
        Company company = this.companyService.findById(companyId);
        return companyMapper.to(company);
    }

    @GetMapping("/{id}/employees")
    List<ResponseEmployee> getEmployeesByCompanyId(@PathVariable("id") Integer companyId) {
        EmployeeMapper employeeMapper = new EmployeeMapper();
        List<Employee> employees = this.companyService.findEmployeesByCompanyID(companyId);
        return employees.stream().map(employeeMapper::to).collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    Page<ResponseCompany> getCompaniesByPageAndSize(Integer page, Integer pageSize) {
        Page<Company> companies = this.companyService.findAllByPageAndPageSize(--page, pageSize);
        return companies.map(companyMapper::to);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseCompany addCompany(@RequestBody RequestCompany requestCompany) {
        Company company = this.companyService.add(companyMapper.from(requestCompany));
        return companyMapper.to(company);
    }

    @PutMapping("/{id}")
    ResponseCompany updateCompany(@RequestBody RequestCompany requestCompany, @PathVariable("id") Integer companyId) {
        Company company = this.companyService.update(companyId, companyMapper.from(requestCompany));
        return companyMapper.to(company);
    }

    @DeleteMapping("/{id}")
    void deleteCompany(@PathVariable("id") Integer companyId) {
        this.companyService.deleteByID(companyId);
    }
}
