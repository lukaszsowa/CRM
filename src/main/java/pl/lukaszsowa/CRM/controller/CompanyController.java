package pl.lukaszsowa.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.service.UserService;

@Controller
public class CompanyController {

    @Autowired
    UserService userService;

    public void getLoggedUserInfo(Model model) {
        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedUser.getName();
        String fullName = userService.getUser(login).getFirstName() + " " + userService.getUser(login).getLastName();
        String role = userService.getUser(login).getRole().getRole().toUpperCase();
        model.addAttribute("fullName", fullName);
        model.addAttribute("role", role);
    }

    @GetMapping("/companies")
    public String getCompanies(Model model){
        getLoggedUserInfo(model);
        return "companies";
    }

    @GetMapping("/companies/add")
    public String addCompany(Model model){
        model.addAttribute("contact", new Contact());
        getLoggedUserInfo(model);
        return "companies-add";
    }
}
