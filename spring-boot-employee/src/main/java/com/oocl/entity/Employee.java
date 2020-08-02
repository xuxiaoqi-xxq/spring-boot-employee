package com.oocl.entity;

import javax.persistence.*;

@Entity
public class Employee {

    @Id
    private Integer employeeId;
    private String name;
    private String gender;
    private Integer age;
    private Integer salary;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "companyId")
    private Company company;

    public Employee(Integer employeeId, String name, String gender, Integer age, Integer salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.salary = salary;
    }

    public Employee() {

    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
