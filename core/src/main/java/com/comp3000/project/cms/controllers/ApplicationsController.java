package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.Application;
import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.DAC.UserType;
import com.comp3000.project.cms.config.EncryptionConfig;
import com.comp3000.project.cms.forms.RegisterForm;
import com.comp3000.project.cms.repository.RegApplicationRepository;
import com.comp3000.project.cms.repository.UserRepository;
import com.comp3000.project.cms.repository.UserTypeRepository;
import com.comp3000.project.cms.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Optional;

/*  ProfessorController

    Handles the following routes:
        GET
            /applications
            /applications/register
            /applications/{application_id}
        POST
            /applications/register
        DELETE
            /applications/{application_id}
*/
@Controller
@RequestMapping("/applications")
public class ApplicationsController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private RegApplicationRepository applicationRepository;
    @Autowired
    private UserTypeRepository userTypeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EncryptionConfig encryptionConfig;
    @Autowired
    private EmailService emailService;
    private String pswd = "MY_COOL_PASSWORD_COMP3004";


    @GetMapping()
    public String listApplications(Model model) {
        log.info("Application list requested");

        Iterable<RegApplication> applications = applicationRepository.findAll();
        model.addAttribute("applications", applications);

        return "applications";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        log.info("Registration form requested");

        model.addAttribute("application", new RegApplication());
        model.addAttribute("received", false);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegApplication application, Model model) {
        log.info("Registration request received");

        // TODO: Add fail cases and submission with the same email
        try {
            applicationRepository.save(application);

            model.addAttribute("received", true);
        } catch (Exception e) {
            log.error(e.toString());
        }

        // Registration service call goes here
        return "register";
    }

    @GetMapping("/applications/{application_id}")
    public String viewApplication(@PathVariable String application_id,
                                  Model model) {
        log.info("Application page for application " + application_id + " requested");

        // Application list retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "application";
    }

    @PostMapping("/{application_id}/approve")
    public RedirectView resolveApplication(@PathVariable String application_id) {
        log.info("Request to approve application " + application_id + " received");

        try {
            Integer id = Integer.valueOf(application_id);

            Optional<RegApplication> applQuery = applicationRepository.findById(id);
            RegApplication appl = applQuery.orElseThrow();
            UserType type;

            if (appl.getBirthDate() == null) {
                type = userTypeRepository.findByType("PROFESSOR");
            } else {
                type = userTypeRepository.findByType("STUDENT");
            }

            String password = encryptionConfig.getPassordEncoder().encode(pswd);
            User newUser = new User(appl.getFirstName(), appl.getLastName(),
                    appl.getEmail(), password, type, appl.getBirthDate());

            User u = userRepository.save(newUser);
            applicationRepository.deleteById(id);

            this.emailService.sendSimpleMessage(appl.getEmail(),
                    appl.getFirstName(), pswd, true);
        } catch (Exception e) {
            log.error(e.toString());
        }
        return new RedirectView("/applications");
    }

    @PostMapping("/{application_id}/deny")
    public RedirectView denyApplication(@PathVariable String application_id) {
        log.info("Request to deny application " + application_id + " received");

        // Application resolving service call goes here
        try {
            Integer id = Integer.valueOf(application_id);
            Optional<RegApplication> applQuery = applicationRepository.findById(id);
            RegApplication appl = applQuery.orElseThrow();

            this.emailService.sendSimpleMessage(appl.getEmail(),
                    appl.getFirstName(), false);
            applicationRepository.deleteById(appl.getId());
        } catch (Exception e) {
            log.error(e.toString());
        }
        return new RedirectView("/applications");
    }
}
