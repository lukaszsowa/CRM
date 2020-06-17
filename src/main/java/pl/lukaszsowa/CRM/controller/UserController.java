package pl.lukaszsowa.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import pl.lukaszsowa.CRM.model.User;
import pl.lukaszsowa.CRM.service.UserService;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/save-user")
    public String saveUser(User user, Model model){
        model.addAttribute("user", new User());
        userService.addUser(user);
        return "home";
    }
}
