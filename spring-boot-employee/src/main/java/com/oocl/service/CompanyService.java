package com.oocl.service;

import com.oocl.dto.RequestCompany;
import com.oocl.dto.ResponseCompany;
import com.oocl.dto.ResponseEmployee;
import com.oocl.entity.Company;
import com.oocl.entity.Employee;
import com.oocl.repository.CompanyRepository;
import com.oocl.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(Integer companyId) {
        return null;
    }

    public List<Employee> findEmployeesByCompanyID(Integer companyId) {
        return null;
    }

    public Page<Company> findAllByPageAndPageSize(Integer page, Integer pageSize) {
        return null;
    }

    public Company add(Company newCompany) {
        return null;
    }

    public Company update(Integer companyId, Company newCompany) {
        return null;
    }

    public void deleteByID(Integer companyId) {

    }

    public Company findByID(Integer companyId) {
        return null;
    }
}
