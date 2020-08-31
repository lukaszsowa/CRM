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
public class HomeController {

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
        return "home";
    }

    @GetMapping("/home")
    public String getIndex(Model model){
        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedUser.getName();
        String fullName = userService.getUser(login).getFirstName() + " " + userService.getUser(login).getLastName();
        String role = userService.getUser(login).getRole().getRole().toUpperCase();
        model.addAttribute("fullName", fullName);
        model.addAttribute("role", role);
        model.addAttribute("contactsCount", contactService.getContactsCount());
        model.addAttribute("companiesCount", companyService.getCompanyCount());
        model.addAttribute("trainingsCount", trainingService.getTrainingsCount());
        return "index";
    }

    @GetMapping("/home/charts")
    public String getPieChart(Model model) {
        Map<String, Integer> graphData = new TreeMap<>();
        graphData.put("2016", 147);
        graphData.put("2017", 1256);
        graphData.put("2018", 3856);
        graphData.put("2019", 19807);
        model.addAttribute("chartData", graphData);
        return "home-charts";
    }
}
