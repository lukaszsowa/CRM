package pl.lukaszsowa.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.model.Training;
import pl.lukaszsowa.CRM.service.TrainingService;
import pl.lukaszsowa.CRM.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class TrainingController {

    @Autowired
    UserService userService;

    @Autowired
    TrainingService trainingService;

    public void getLoggedUserInfo(Model model) {
        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedUser.getName();
        String fullName = userService.getUser(login).getFirstName() + " " + userService.getUser(login).getLastName();
        String role = userService.getUser(login).getRole().getRole().toUpperCase();
        model.addAttribute("fullName", fullName);
        model.addAttribute("role", role);
    }

    @GetMapping("/training")
    public String getTrainings(Model model) {
        getLoggedUserInfo(model);
        model.addAttribute("trainingsList", trainingService.getAllTrainings());
        return "training";
    }

    @GetMapping("/training/add")
    public String addTraining(Model model) {
        getLoggedUserInfo(model);
        model.addAttribute("training", new Training());
        return "training-add";
    }

    @PostMapping("/save-training")
    public String saveTraining(@Valid @ModelAttribute Training training, BindingResult bindingResult, Model model){
        getLoggedUserInfo(model);
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return "training-add";
        } else {
            model.addAttribute("training", new Training());
            trainingService.addTraining(training);


        }
        return "redirect:/training/" + training.getId();
    }

    @RequestMapping(value = "/training/delete/{id}", method = RequestMethod.GET)
    public String deleteTraining(@PathVariable("id") long id){
        trainingService.deleteTraining(id);
        return "redirect:/training";
    }

    @RequestMapping(value = "/training/{id}", method = RequestMethod.GET)
    public String getTraining(@PathVariable("id") long id, Model model){
        getLoggedUserInfo(model);
        Optional<Training> trainingOptional = trainingService.getTrainingById(id);
        Training training = trainingOptional.get();
        model.addAttribute("training", training);
        return "training-details";
    }

    @RequestMapping(value = "/training/edit/{id}", method = RequestMethod.GET)
    public String getTrainingEdit(@PathVariable("id") long id, Model model){
        getLoggedUserInfo(model);
        Optional<Training> trainingOptional = trainingService.getTrainingById(id);
        Training training = trainingOptional.get();
        model.addAttribute("training", training);
        return "training-add";
    }




}