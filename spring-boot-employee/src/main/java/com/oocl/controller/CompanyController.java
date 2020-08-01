package com.oocl.controller;

import com.oocl.dto.RequestCompany;
import com.oocl.dto.ResponseCompany;
import com.oocl.dto.ResponseEmployee;
import com.oocl.mapper.CompanyMapper;
import com.oocl.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return this.companyService.findById(companyId);
    }

    @GetMapping("/{id}/employees")
    List<ResponseEmployee> getEmployeesByCompanyId(@PathVariable("id") Integer companyId) {
        return this.companyService.findEmployeesByCompanyID(companyId);
    }

    @GetMapping(params = {"page", "pageSize"})
    Page<ResponseCompany> getCompaniesByPageAndSize(Integer page, Integer pageSize) {
        return this.companyService.findAllByPageAndPageSize(--page, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseCompany addCompany(@RequestBody RequestCompany requestCompany) {
        return this.companyService.add(companyMapper.from(requestCompany));
    }

    @PutMapping("/{id}")
    ResponseCompany updateCompany(@RequestBody RequestCompany requestCompany, @PathVariable("id") Integer companyId) {
        return this.companyService.update(companyId, companyMapper.from(requestCompany));
    }
}
