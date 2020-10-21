package pl.lukaszsowa.CRM.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.model.Training;
import pl.lukaszsowa.CRM.service.ContactService;
import pl.lukaszsowa.CRM.service.TrainingService;
import pl.lukaszsowa.CRM.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class TrainingController {

    @Autowired
    UserService userService;

    @Autowired
    TrainingService trainingService;

    @Autowired
    ContactService contactService;

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

    @GetMapping("/training/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Training> trainingList = trainingService.getAllTrainings();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Date start", "Date end", "Localization", "Trainer", "Capacity"};
        String[] nameMapping = {"dateStart", "dateEnd", "localization", "trainer", "capacity"};

        csvWriter.writeHeader(csvHeader);

        for (Training training : trainingList) {
            csvWriter.write(training, nameMapping);
        }

        csvWriter.close();

    }

    @GetMapping("/training/export-excel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=trainings.xlsx");
        ByteArrayInputStream stream = ExcelFileExporter.trainingListToExcelFile(trainingService.getAllTrainings());
        IOUtils.copy(stream, response.getOutputStream());
    }

    @RequestMapping(value = "/training/generate-pdf", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> trainingsPdf() throws IOException {

        List<Training> trainingList = (List<Training>) trainingService.getAllTrainings();

        ByteArrayInputStream bis = GeneratePdfReport.trainingsPdf(trainingList);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=trainings_report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @RequestMapping(value = "/training/{id}/participants", method = RequestMethod.GET)
    public String getParticipants(@PathVariable("id") long id, Model model){
        getLoggedUserInfo(model);
        Optional<Training> trainingOptional = trainingService.getTrainingById(id);
        Training training = trainingOptional.get();
        model.addAttribute("training", training);
        List<Contact> contactList = contactService.getParticipants(id);
        model.addAttribute("participants", contactList);
        return "training-participants";
    }

    @RequestMapping(value = "/training/{id}/training-participants-add", method = RequestMethod.GET)
    public String getChooseParticipant(@PathVariable("id") long id, Model model){
        Optional<Training> trainingOptional = trainingService.getTrainingById(id);
        Training training = trainingOptional.get();
        model.addAttribute("training", training);
        List<Contact> contactList = contactService.getContacts();
        model.addAttribute("contactList", contactList);
        return "training-participants-choice";
    }

    @Transactional
    @RequestMapping(value = "/training/{id}/participant/{id2}", method = RequestMethod.GET)
    public String addRelation(@PathVariable("id") long id, @PathVariable("id2") long id2, Model model){
        Optional<Training> trainingOptional = trainingService.getTrainingById(id);
        Training training = trainingOptional.get();
        model.addAttribute("training", training);
        List<Contact> contactList = contactService.getContacts();
        model.addAttribute("contactList", contactList);
        Query query = entityManager.createNativeQuery("INSERT INTO trainings_contacts (training_id, contact_id) values (:id,:id2)");
        query.setParameter("id", id);
        query.setParameter("id2", id2);
        query.executeUpdate();
        return "redirect:/training/{id}/participants";
    }


}