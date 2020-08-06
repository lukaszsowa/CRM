package pl.lukaszsowa.CRM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lukaszsowa.CRM.model.Company;
import pl.lukaszsowa.CRM.repository.CompanyRepository;
import pl.lukaszsowa.CRM.repository.ContactRepository;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public Company addCompany(Company company){
        return companyRepository.save(company);
    }
}
