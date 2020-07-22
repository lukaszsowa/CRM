package pl.lukaszsowa.CRM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lukaszsowa.CRM.controller.ContactsController;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.model.User;
import pl.lukaszsowa.CRM.repository.ContactRepository;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    public Contact addContact(Contact contact){
        return contactRepository.save(contact);
    }

    public List<Contact> getContacts(){
        return contactRepository.findAll();
    }
}
