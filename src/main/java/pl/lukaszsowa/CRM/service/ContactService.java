package pl.lukaszsowa.CRM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lukaszsowa.CRM.controller.ContactsController;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.model.Training;
import pl.lukaszsowa.CRM.model.User;
import pl.lukaszsowa.CRM.repository.ContactRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    EntityManager entityManager;

    public Contact addContact(Contact contact){
        return contactRepository.save(contact);
    }

    public List<Contact> getContacts(){
        return contactRepository.findAll();
    }

    public void deleteContact(long id){
        contactRepository.deleteById(id);
    }

    public Optional<Contact> getContactById(long id){
       return contactRepository.findById(id);
    }

    public List<Contact> getContactsByCompanyId(long id){
        return (List<Contact>) contactRepository.getContactsByCompany(id);
    }

    public Long getContactsCount(){
        return contactRepository.count();
    }

    public List<Contact> getParticipants(long id){
        return contactRepository.getParticipants(id);
    }



}
