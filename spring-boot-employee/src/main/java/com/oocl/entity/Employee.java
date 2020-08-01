package com.oocl.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {

    @Id
    private Integer employeeId;
    private Integer companyId;
    private final String name;
    private final String gender;
    private final Integer age;
    private final Integer salary;

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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
