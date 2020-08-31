package pl.lukaszsowa.CRM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lukaszsowa.CRM.model.Company;
import pl.lukaszsowa.CRM.repository.CompanyRepository;
import pl.lukaszsowa.CRM.repository.ContactRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public Company addCompany(Company company){
        return companyRepository.save(company);
    }

    public List<Company> getCompanies(){
        return companyRepository.findAll();
    }

    public void deleteCompany(long id){
        companyRepository.deleteById(id);
    }

    public Optional<Company> getCompanyById(long id){
        return companyRepository.findById(id);
    }

    public Long getCompanyCount(){
        return companyRepository.count();
    }
}
