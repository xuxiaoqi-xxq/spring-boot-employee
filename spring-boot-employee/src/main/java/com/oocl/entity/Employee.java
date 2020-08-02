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
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "companyId")
    private Company company;

    public Employee(Integer employeeId, String name, String gender, Integer age, Integer salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.salary = salary;
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

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getSalary() {
        return salary;
    }
}
