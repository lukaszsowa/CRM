package pl.lukaszsowa.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.model.User;
import pl.lukaszsowa.CRM.service.ContactService;
import pl.lukaszsowa.CRM.service.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
public class ContactsController {

    @Autowired
    UserService userService;

    @Autowired
    ContactService contactService;

    public void getLoggedUserInfo(Model model) {
        model.addAttribute("usersList", userService.getUsers());
        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedUser.getName();
        String fullName = userService.getUser(login).getFirstName() + " " + userService.getUser(login).getLastName();
        String role = userService.getUser(login).getRole().getRole().toUpperCase();
        model.addAttribute("fullName", fullName);
        model.addAttribute("role", role);
    }

    @GetMapping("/contacts")
    public String getContact(){
        return "contacts";
    }

    @GetMapping("contacts/add")
    public String addUser(Model model){
        model.addAttribute("contact", new Contact());
        getLoggedUserInfo(model);
        return "contacts-add";
    }

    @PostMapping("/save-contact")
    public String saveContact(@Valid @ModelAttribute Contact contact, BindingResult bindingResult, Model model){
        getLoggedUserInfo(model);
        if(bindingResult.hasErrors()){
            return "contacts-add";
        } else {
            model.addAttribute("contact", new Contact());
            contact.setCreateTime(LocalDateTime.now());
            contactService.addContact(contact);
        }
        return "redirect:/contacts";
    }
}
