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
import pl.lukaszsowa.CRM.model.Company;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.service.CompanyService;
import pl.lukaszsowa.CRM.service.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
public class CompanyController {

    @Autowired
    UserService userService;

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

    @GetMapping("/companies")
    public String getCompanies(Model model){
        getLoggedUserInfo(model);
        return "companies";
    }

    @GetMapping("/companies/add")
    public String addCompany(Model model){
        model.addAttribute("company", new Company());
        getLoggedUserInfo(model);
        return "companies-add";
    }

    @PostMapping("/save-company")
    public String saveCompany(@Valid @ModelAttribute Company company, BindingResult bindingResult, Model model){
        getLoggedUserInfo(model);
        if(bindingResult.hasErrors()){
            return "companies-add";
        } else {
            model.addAttribute("company", new Company());
            companyService.addCompany(company);
        }
        return "redirect:/companies";
    }
}
