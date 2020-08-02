package com.oocl.mapper;

import com.oocl.dto.RequestCompany;
import com.oocl.dto.ResponseCompany;
import com.oocl.entity.Company;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public Company from(RequestCompany requestCompany) {
        Company company = new Company();
        BeanUtils.copyProperties(requestCompany, company);
        return company;
    }

    public ResponseCompany to(Company company) {
        ResponseCompany responseCompany = new ResponseCompany();
        BeanUtils.copyProperties(company, responseCompany);
        return responseCompany;
    }
}
