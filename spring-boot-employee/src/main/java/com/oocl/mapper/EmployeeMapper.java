package com.oocl.mapper;

import com.oocl.dto.RequestEmployee;
import com.oocl.dto.ResponseEmployee;
import com.oocl.entity.Company;
import com.oocl.entity.Employee;
import com.oocl.exception.NoSuchDataException;
import com.oocl.service.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {


    @Autowired
    private CompanyService companyService;

    public Employee from(RequestEmployee requestEmployee) throws NoSuchDataException {
        Employee employee = new Employee();
        BeanUtils.copyProperties(requestEmployee, employee);
        Company company = companyService.findById(requestEmployee.getCompanyId());
        employee.setCompany(company);
        return employee;
    }

    public ResponseEmployee to(Employee employee) {
        ResponseEmployee responseEmployee = new ResponseEmployee();
        BeanUtils.copyProperties(employee, responseEmployee);
        responseEmployee.setCompanyId(employee.getCompany().getCompanyId());
        return responseEmployee;
    }
}
