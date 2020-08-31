package pl.lukaszsowa.CRM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.model.User;

import java.util.List;

public interface ContactRepository extends JpaRepository <Contact, Long> {

    @Query("SELECT c FROM Contact c where c.company.id=:id")
    List<Contact> getContactsByCompany(@Param("id") long id);



}
