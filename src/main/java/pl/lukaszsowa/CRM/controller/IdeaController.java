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
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.model.Idea;
import pl.lukaszsowa.CRM.model.User;
import pl.lukaszsowa.CRM.service.IdeaService;
import pl.lukaszsowa.CRM.service.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
public class IdeaController {

    @Autowired
    UserService userService;

    @Autowired
    IdeaService ideaService;

    public void getLoggedUserInfo(Model model) {
        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedUser.getName();
        String fullName = userService.getUser(login).getFirstName() + " " + userService.getUser(login).getLastName();
        String role = userService.getUser(login).getRole().getRole().toUpperCase();
        model.addAttribute("fullName", fullName);
        model.addAttribute("role", role);
    }

    @GetMapping("/ideas")
    public String getIdeas(Model model){
        getLoggedUserInfo(model);
        model.addAttribute("idea", new Idea());
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        long userId = userService.getUser(loggedUser).getId();
        model.addAttribute("ideaListByUser", ideaService.getIdeasByUserId(userId));
        return "idea";
    }


}
