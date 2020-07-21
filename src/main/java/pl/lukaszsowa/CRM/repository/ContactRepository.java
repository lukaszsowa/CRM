package pl.lukaszsowa.CRM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lukaszsowa.CRM.model.Contact;

public interface ContactRepository extends JpaRepository <Contact, Long> {
}
