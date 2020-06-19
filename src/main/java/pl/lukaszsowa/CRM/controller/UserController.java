package pl.lukaszsowa.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.lukaszsowa.CRM.model.Role;
import pl.lukaszsowa.CRM.model.User;
import pl.lukaszsowa.CRM.service.RoleService;
import pl.lukaszsowa.CRM.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @PostMapping("/save-user")
    public String saveUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "home";
        } else {
            model.addAttribute("user", new User());
            user.setRole(roleService.getRole("user"));
            userService.addUser(user);
            return "home";
        }
    }
}
