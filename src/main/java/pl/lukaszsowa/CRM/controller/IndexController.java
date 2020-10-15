package pl.lukaszsowa.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.service.CompanyService;
import pl.lukaszsowa.CRM.service.ContactService;
import pl.lukaszsowa.CRM.service.TrainingService;
import pl.lukaszsowa.CRM.service.UserService;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    ContactService contactService;

    @Autowired
    CompanyService companyService;

    @Autowired
    TrainingService trainingService;

    @GetMapping("/index")
    String getIndex(Model model){
        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedUser.getName();
        String fullName = userService.getUser(login).getFirstName() + " " + userService.getUser(login).getLastName();
        String role = userService.getUser(login).getRole().getRole().toUpperCase();
        model.addAttribute("fullName", fullName);
        model.addAttribute("role", role);
        model.addAttribute("contactsCount", contactService.getContactsCount());
        model.addAttribute("companiesCount", companyService.getCompanyCount());
        model.addAttribute("trainingsCount", trainingService.getTrainingsCount());
        return "index";
    }

    @GetMapping("/test")
    String getTest(Model model){
        getLoggedUserInfo(model);
        return "test";
    }

    public void getLoggedUserInfo(Model model) {
        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedUser.getName();
        String fullName = userService.getUser(login).getFirstName() + " " + userService.getUser(login).getLastName();
        String role = userService.getUser(login).getRole().getRole().toUpperCase();
        model.addAttribute("fullName", fullName);
        model.addAttribute("role", role);
    }

    @GetMapping("/settings")
    String getSettings(Model model){
        getLoggedUserInfo(model);
        return "settings";
    }
}

