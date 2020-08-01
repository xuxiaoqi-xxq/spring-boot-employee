package com.oocl.service;

import com.oocl.dto.ResponseCompany;
import com.oocl.dto.ResponseEmployee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    public List<ResponseCompany> findAll() {
        return null;
    }

    public ResponseCompany findById(Integer companyId) {
        return null;
    }

    public List<ResponseEmployee> findEmployeesByCompanyID(Integer companyId) {
        return null;
    }

    public Page<ResponseCompany> findAllByPageAndPageSize(Integer page, Integer pageSize) {
        return null;
    }
}
