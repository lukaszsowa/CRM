package pl.lukaszsowa.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.model.User;
import pl.lukaszsowa.CRM.service.CompanyService;
import pl.lukaszsowa.CRM.service.ContactService;
import pl.lukaszsowa.CRM.service.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class ContactsController {

    @Autowired
    UserService userService;

    @Autowired
    ContactService contactService;

    @Autowired
    CompanyService companyService;

    public void getLoggedUserInfo(Model model) {
        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedUser.getName();
        String fullName = userService.getUser(login).getFirstName() + " " + userService.getUser(login).getLastName();
        String role = userService.getUser(login).getRole().getRole().toUpperCase();
        model.addAttribute("fullName", fullName);
        model.addAttribute("role", role);
    }

    @GetMapping("/contacts")
    public String getContact(Model model){
        getLoggedUserInfo(model);
        model.addAttribute("contacts", contactService.getContacts());
        return "contact-list";
    }

    @GetMapping("/contacts/contact-company-choice")
    public String getPopup(Model model){
        model.addAttribute("companiesList", companyService.getCompanies());
        return "contact-company-choice";
    }

    @GetMapping("contacts/add")
    public String addUser(Model model){
        model.addAttribute("contact", new Contact());
        model.addAttribute("companiesList", companyService.getCompanies());
        getLoggedUserInfo(model);
        return "contact-add";
    }

    @PostMapping("/save-contact")
    public String saveContact(@Valid @ModelAttribute Contact contact, BindingResult bindingResult, Model model){
        getLoggedUserInfo(model);
        if(bindingResult.hasErrors()){
            return "contact-add";
        } else {
            model.addAttribute("contact", new Contact());
            contact.setCreateTime(LocalDateTime.now());
            contactService.addContact(contact);
        }
        return "redirect:/contacts/" + contact.getId();
    }

    @RequestMapping(value = "/contacts/delete/{id}", method = RequestMethod.GET)
    public String deleteContact(@PathVariable("id") long id){
        contactService.deleteContact(id);
        return "redirect:/contacts";
    }

    @RequestMapping(value = "/contacts/{id}", method = RequestMethod.GET)
    public String getContactDetails(@PathVariable("id") long id, Model model){
        getLoggedUserInfo(model);
        Optional<Contact> optionalContact = contactService.getContactById(id);
        Contact contact = optionalContact.get();
        model.addAttribute("contact", contact);
        return "contact-details";
    }

    @RequestMapping(value = "/contacts/edit/{id}", method = RequestMethod.GET)
    public String getContactEdit(@PathVariable("id") long id, Model model){
        getLoggedUserInfo(model);
        Optional<Contact> optionalContact = contactService.getContactById(id);
        Contact contact = optionalContact.get();
        model.addAttribute("contact", contact);
        return "contact-add";
    }
}
