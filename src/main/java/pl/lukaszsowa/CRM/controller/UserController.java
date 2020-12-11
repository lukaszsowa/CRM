package pl.lukaszsowa.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import pl.lukaszsowa.CRM.model.Role;
import pl.lukaszsowa.CRM.model.User;
import pl.lukaszsowa.CRM.service.RoleService;
import pl.lukaszsowa.CRM.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    public void getLoggedUserInfo(Model model) {
        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedUser.getName();
        String fullName = userService.getUser(login).getFirstName() + " " + userService.getUser(login).getLastName();
        String role = userService.getUser(login).getRole().getRole().toUpperCase();
        model.addAttribute("fullName", fullName);
        model.addAttribute("role", role);
    }


    @GetMapping("/users")
    public String getUsersPage(Model model){
        model.addAttribute("usersList", userService.getUsers());
        getLoggedUserInfo(model);
        return "users";
    }

    @GetMapping("users/add")
    public String addUser(Model model){
        model.addAttribute("user", new User());
//        getLoggedUserInfo(model);
        return "users-add";
    }

    @PostMapping("/save-user")
    public String saveUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model){
        getLoggedUserInfo(model);
        if(bindingResult.hasErrors()){
            return "users-add";
        } else {
            model.addAttribute("user", new User());
            user.setRole(roleService.getRole("user"));
            userService.addUser(user);
            model.addAttribute("usersList", userService.getUsers());
        }
        return "redirect:/users";
    }

    @PostMapping("/save-changes")
    public String saveChanges(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model){
        getLoggedUserInfo(model);
        if (bindingResult.hasErrors()){
            return "users-details-edit";
        } else {
            model.addAttribute("user", new User());
            user.setRole(roleService.getRole("user"));
            userService.addUser(user);
            model.addAttribute("usersList", userService.getUsers());
        }
        return "redirect:/users";
    }

    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") long id){
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String getUserDetails(@PathVariable("id") long id, Model model){
        getLoggedUserInfo(model);
        Optional<User> optionalUser = userService.getUserById(id);
        User user = optionalUser.get();
        model.addAttribute("user", user);
        return "users-details";
    }
}
