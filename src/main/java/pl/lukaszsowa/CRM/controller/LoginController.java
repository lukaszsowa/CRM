package pl.lukaszsowa.CRM.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lukaszsowa.CRM.model.User;
import pl.lukaszsowa.CRM.service.CompanyService;
import pl.lukaszsowa.CRM.service.ContactService;
import pl.lukaszsowa.CRM.service.TrainingService;
import pl.lukaszsowa.CRM.service.UserService;

import java.util.Map;
import java.util.TreeMap;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    ContactService contactService;

    @Autowired
    CompanyService companyService;

    @Autowired
    TrainingService trainingService;

    @GetMapping("/")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/errorLogin")
    String getErrorLogin(){
        return "login-error";
    }

}
