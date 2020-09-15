package pl.lukaszsowa.CRM.controller;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import pl.lukaszsowa.CRM.model.Company;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.service.CompanyService;
import pl.lukaszsowa.CRM.service.ContactService;
import pl.lukaszsowa.CRM.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class CompanyController {

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    ContactService contactService;

    public void getLoggedUserInfo(Model model) {
        Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedUser.getName();
        String fullName = userService.getUser(login).getFirstName() + " " + userService.getUser(login).getLastName();
        String role = userService.getUser(login).getRole().getRole().toUpperCase();
        model.addAttribute("fullName", fullName);
        model.addAttribute("role", role);
    }

    @GetMapping("/companies")
    public String getCompanies(Model model){
        getLoggedUserInfo(model);
        model.addAttribute("companiesList", companyService.getCompanies());
        return "company";
    }

    @RequestMapping(value = "/companies/delete/{id}", method = RequestMethod.GET)
    public String deleteCompany(@PathVariable("id") long id, Model model){
        companyService.deleteCompany(id);
        return "redirect:/companies";
    }

    @RequestMapping(value="/companies/{id}", method = RequestMethod.GET)
    public String getCompanyDetails(@PathVariable("id") long id, Model model){
        getLoggedUserInfo(model);
        Optional<Company> optionalCompany = companyService.getCompanyById(id);
        Company company = optionalCompany.get();
        model.addAttribute("company", company);
        return "company-details";
    }

    @RequestMapping(value="/companies/{id}/related-contacts", method = RequestMethod.GET)
    public String getRelatedContacts(@PathVariable("id") long id, Model model){
        getLoggedUserInfo(model);
        Optional<Company> optionalCompany = companyService.getCompanyById(id);
        Company company = optionalCompany.get();
        model.addAttribute("company", company);
        model.addAttribute("relatedContacts", contactService.getContactsByCompanyId(id));
        return "company-details-related-contacts";
    }

    @RequestMapping(value="/companies/edit/{id}", method = RequestMethod.GET)
    public String getCompanyEdit(@PathVariable("id") long id, Model model){
        getLoggedUserInfo(model);
        Optional<Company> optionalCompany = companyService.getCompanyById(id);
        Company company = optionalCompany.get();
        model.addAttribute("company", company);
        return "company-add";
    }

    @GetMapping("/companies/add")
    public String addCompany(Model model){
        model.addAttribute("company", new Company());
        getLoggedUserInfo(model);
        return "company-add";
    }

    @PostMapping("/save-company")
    public String saveCompany(@Valid @ModelAttribute Company company, BindingResult bindingResult, Model model){
        getLoggedUserInfo(model);
        if(bindingResult.hasErrors()){
            return "company-add";
        } else {
            model.addAttribute("company", new Company());
            companyService.addCompany(company);
        }
        return "redirect:/companies/" + company.getId();
    }

    @GetMapping("/companies/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Company> companyList = companyService.getCompanies();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Company name", "NIP", "Segment", "Industry", "City"};
        String[] nameMapping = {"companyName", "nip", "segment", "industry", "city"};

        csvWriter.writeHeader(csvHeader);

        for (Company company : companyList) {
            csvWriter.write(company, nameMapping);
        }

        csvWriter.close();

    }

    @GetMapping("/companies/export-excel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=companies.xlsx");
        ByteArrayInputStream stream = ExcelFileExporter.companyListToExcelFile(companyService.getCompanies());
        IOUtils.copy(stream, response.getOutputStream());
    }
}
