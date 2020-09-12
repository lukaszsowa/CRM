package pl.lukaszsowa.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.model.User;
import pl.lukaszsowa.CRM.service.CompanyService;
import pl.lukaszsowa.CRM.service.ContactService;
import pl.lukaszsowa.CRM.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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

    @GetMapping("/contacts/edit/contact-company-choice")
    public String getPopupEdit(Model model){
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
        model.addAttribute("companiesList", companyService.getCompanies());
        return "contact-add";
    }

    @GetMapping("/contacts/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Contact> contactList = contactService.getContacts();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"First name", "Last name", "E-mail", "Phone", "Job title"};
        String[] nameMapping = {"firstName", "lastName", "email", "phone", "jobTitle"};

        csvWriter.writeHeader(csvHeader);

        for (Contact contact : contactList) {
            csvWriter.write(contact, nameMapping);
        }

        csvWriter.close();

    }
}
