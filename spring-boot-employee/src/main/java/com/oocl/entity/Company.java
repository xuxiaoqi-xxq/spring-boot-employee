package com.oocl.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;
    private String name;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "company")
    private List<Employee> employees;

    public Company(Integer companyId, String name, List<Employee> employees) {
        this.companyId = companyId;
        this.name = name;
        this.employees = employees;
    }

    public Company() {

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
