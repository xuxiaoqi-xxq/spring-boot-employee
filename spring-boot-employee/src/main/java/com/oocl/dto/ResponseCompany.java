package com.oocl.dto;

import com.oocl.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponseCompany {

    private Integer companyId;
    private String name;
    private List<Employee> employees;

    public ResponseCompany() {

    }

    public ResponseCompany(Integer companyId, String name, List<Employee> employees) {
        this.companyId = companyId;
        this.name = name;
        this.employees = employees;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
