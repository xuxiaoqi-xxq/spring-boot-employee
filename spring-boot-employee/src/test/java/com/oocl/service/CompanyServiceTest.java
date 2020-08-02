package com.oocl.service;

import com.oocl.entity.Company;
import com.oocl.repository.CompanyRepository;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CompanyServiceTest {

    @Test
    void should_return_all_companies_when_find_all_given() {
        //given
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository, null);
        List<Company> companies = Collections.singletonList(new Company(1, "OOCL", null));
        given(companyRepository.findAll()).willReturn(companies);

        //when
        List<Company> allCompanies = companyService.findAll();

        //then
        assertEquals(companies, allCompanies);
    }
}
