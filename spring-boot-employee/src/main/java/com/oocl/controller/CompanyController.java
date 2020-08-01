package com.oocl.controller;

import com.oocl.dto.ResponseCompany;
import com.oocl.service.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping()
    List<ResponseCompany> getCompanies() {
        return this.companyService.findAll();
    }

    @GetMapping("/{id}")
    ResponseCompany getCompanyById(@PathVariable("id") Integer companyId) {
        return this.companyService.findById(companyId);
    }
}
