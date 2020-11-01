package pl.lukaszsowa.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.model.Idea;
import pl.lukaszsowa.CRM.model.Training;
import pl.lukaszsowa.CRM.model.User;
import pl.lukaszsowa.CRM.service.IdeaService;
import pl.lukaszsowa.CRM.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class IdeaController {

    @Autowired
    UserService userService;

    @Autowired
    IdeaService ideaService;

    @Autowired
    EntityManager entityManager;

    public void getLoggedUserInfo(Model model) {
        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedUser.getName();
        String fullName = userService.getUser(login).getFirstName() + " " + userService.getUser(login).getLastName();
        String role = userService.getUser(login).getRole().getRole().toUpperCase();
        model.addAttribute("fullName", fullName);
        model.addAttribute("role", role);
    }

    @PostMapping("/save-idea")
    public String saveIdea(@Valid @ModelAttribute Idea idea, BindingResult bindingResult, Model model){
        getLoggedUserInfo(model);
        model.addAttribute("idea", new Idea());
        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedUser.getName();
        idea.setUser(userService.getUser(login));
        idea.setStatus("To do!");
        ideaService.addIdea(idea);
        return "redirect:/index";
    }

    @GetMapping("/ideas")
    public String getIdeas(Model model){
        getLoggedUserInfo(model);
        model.addAttribute("idea", new Idea());
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        long userId = userService.getUser(loggedUser).getId();
        model.addAttribute("ideaListByUser", ideaService.getAllIdeasByUserId(userService.getUser(loggedUser).getId()));
        return "idea";
    }

    @RequestMapping(value = "/idea/delete/{id}", method = RequestMethod.GET)
    public String deleteIdea(@PathVariable("id") long id){
        ideaService.deleteIdea(id);
        return "redirect:/ideas";
    }

    @Transactional
    @RequestMapping(value = "/idea/done/{id}", method = RequestMethod.GET)
    public String setStatusDone(@PathVariable("id") long id){
        Query query = entityManager.createNativeQuery("UPDATE idea SET idea.status = 'Done!' where idea.id =:id");
        query.setParameter("id", id);
        query.executeUpdate();
        return "redirect:/ideas";
    }

    @Transactional
    @RequestMapping(value = "/idea/to-do/{id}", method = RequestMethod.GET)
    public String setStatusToDo(@PathVariable("id") long id){
        Query query = entityManager.createNativeQuery("UPDATE idea SET idea.status = 'To do!' where idea.id =:id");
        query.setParameter("id", id);
        query.executeUpdate();
        return "redirect:/ideas";
    }
}
