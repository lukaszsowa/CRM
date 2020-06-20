package pl.lukaszsowa.CRM.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lukaszsowa.CRM.model.User;

@Controller
public class HomeController {

    @GetMapping("/")
    public String loginPage(){
        return "home";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getHome(User user, Model model){
        model.addAttribute("user", new User());
        return "registration";
    }
}
