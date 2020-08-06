package pl.lukaszsowa.CRM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lukaszsowa.CRM.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
