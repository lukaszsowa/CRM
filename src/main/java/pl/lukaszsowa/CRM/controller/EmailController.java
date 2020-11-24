package pl.lukaszsowa.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.lukaszsowa.CRM.model.Idea;
import pl.lukaszsowa.CRM.service.*;

@Controller
public class EmailController {

    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailController(EmailSender emailSender,
                           TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @Autowired
    UserService userService;

    @Autowired
    IdeaService ideaService;

    @Autowired
    CompanyService companyService;

    @Autowired
    ContactService contactService;

    @Autowired
    TrainingService trainingService;

    @RequestMapping("/email")
    public String send(Model model) {
        Context context = new Context();
        context.setVariable("header", "Newsletter");
        context.setVariable("title", "Tytuł");
        context.setVariable("description", "Opis");
        String body = templateEngine.process("template", context);
        emailSender.sendEmail("lukaszsowa@onet.pl", "CodeCouple Newsletter", body);
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
}


