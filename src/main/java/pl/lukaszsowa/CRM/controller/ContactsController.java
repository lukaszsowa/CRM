package pl.lukaszsowa.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.lukaszsowa.CRM.model.User;
import pl.lukaszsowa.CRM.service.UserService;

@Controller
public class ContactsController {

    @Autowired
    UserService userService;

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
        model.addAttribute("user", new User());
        getLoggedUserInfo(model);
        return "contacts-add";
    }
}
