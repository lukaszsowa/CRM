package pl.lukaszsowa.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.model.Idea;
import pl.lukaszsowa.CRM.service.*;

import javax.validation.Valid;

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

    @Autowired
    IdeaService ideaService;

    @GetMapping("/index")
    String getIndex(Model model){
        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedUser.getName();
        String fullName = userService.getUser(login).getFirstName() + " " + userService.getUser(login).getLastName();
        String role = userService.getUser(login).getRole().getRole().toUpperCase();
        long userId = userService.getUser(loggedUser.getName()).getId();
        model.addAttribute("ideaListByUser", ideaService.getIdeasByUserId(userId));
        model.addAttribute("fullName", fullName);
        model.addAttribute("role", role);
        model.addAttribute("contactsCount", contactService.getContactsCount());
        model.addAttribute("companiesCount", companyService.getCompanyCount());
        model.addAttribute("trainingsCount", trainingService.getTrainingsCount());
        model.addAttribute("idea", new Idea());
        return "index";
    }

    @GetMapping("/test")
    String getTest(Model model){
        getLoggedUserInfo(model);
        return "test";
    }

    @GetMapping("/csv-import")
    String getImport(){
        return "import";
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

