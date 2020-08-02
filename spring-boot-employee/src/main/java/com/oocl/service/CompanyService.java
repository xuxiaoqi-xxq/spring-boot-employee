package com.oocl.service;

import com.oocl.dto.RequestCompany;
import com.oocl.dto.ResponseCompany;
import com.oocl.dto.ResponseEmployee;
import com.oocl.entity.Company;
import com.oocl.entity.Employee;
import com.oocl.repository.CompanyRepository;
import com.oocl.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public List<Employee> findEmployeesByCompanyId(Integer companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if(company != null){
            return company.getEmployees();
        }
        return null;
    }

    public Page<Company> findAllByPageAndPageSize(Integer page, Integer pageSize) {
        return companyRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Company add(Company newCompany) {
        return companyRepository.save(newCompany);
    }

    public Company update(Integer companyId, Company newCompany) {
        Company company = this.companyRepository.findById(companyId).orElse(null);
        if (newCompany != null && company != null) {
            company.setName(newCompany.getName());
            this.companyRepository.save(company);
        }
        return company;
    }

    public void deleteById(Integer companyId) {

    }

    public Company findByID(Integer companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }
}
