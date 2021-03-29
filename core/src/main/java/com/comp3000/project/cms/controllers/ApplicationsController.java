package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.BusinessLogic.BusinessLogicHandlerFactory;
import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.exception.CannotCreateException;
import com.comp3000.project.cms.repository.UserTypeRepository;
import com.comp3000.project.cms.services.EmailService;
import com.comp3000.project.cms.services.RegApplication.RegApplicationCommandService;
import com.comp3000.project.cms.services.RegApplication.RegApplicationQueryService;
import com.comp3000.project.cms.services.User.UserCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;
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

    private static final Logger log = LoggerFactory.getLogger(ApplicationsController.class);
    @Autowired
    private UserTypeRepository userTypeRepository;
    @Autowired
    private RegApplicationCommandService regApplicationCommandService;
    @Autowired
    private UserCommandService userCommandService;
    @Autowired
    private RegApplicationQueryService regApplicationQueryService;
    @Autowired
    private EmailService emailService;

    @GetMapping()
    public String listApplications(Model model) {
        log.info("Application list requested");

        Iterable<RegApplication> applications = regApplicationQueryService.getAll();
        model.addAttribute("applications", applications);

        return "applications";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        log.info("Registration form requested");

        model.addAttribute("application", new RegApplication());
        model.addAttribute("status", null);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegApplication application, Model model) {
        log.info("Registration request received");

        try {
            regApplicationCommandService.create(application);
            model.addAttribute("status", Status.ok(0));
        } catch (CannotCreateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

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

            Optional<RegApplication> applQuery = regApplicationQueryService.getById(id);
            RegApplication appl = applQuery.orElseThrow();

            String pswd = userCommandService.createFromApplication(appl);
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
            Optional<RegApplication> applQuery = regApplicationQueryService.getById(id);
            RegApplication appl = applQuery.orElseThrow();

            this.emailService.sendSimpleMessage(appl.getEmail(),
                    appl.getFirstName(), false);
            regApplicationCommandService.deleteById(appl.getId());
        } catch (Exception e) {
            log.error(e.toString());
        }
        return new RedirectView("/applications");
    }
}
