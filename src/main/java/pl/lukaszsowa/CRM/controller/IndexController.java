package pl.lukaszsowa.CRM.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    String getIndex(){
        return "index";
    }

    @GetMapping("/errorLogin")
    String getErrorLogin(){
        return "error-login";
    }
}

