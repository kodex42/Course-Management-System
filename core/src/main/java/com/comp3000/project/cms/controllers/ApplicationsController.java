package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.forms.RegisterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping
    public String listApplications(Model model) {
        log.info("Application list requested");

        // Application list retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "applications";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        log.info("Registration form requested");

        return "register";
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterForm> register(@Valid @RequestBody RegisterForm registerForm) {
        log.info("Registration request received");

        // Registration service call goes here

        return new ResponseEntity<>(registerForm, HttpStatus.OK);
    }

    @GetMapping("/{application_id}")
    public String viewApplication(@PathVariable String application_id,
                                  Model model) {
        log.info("Application page for application " + application_id + " requested");

        // Application list retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "application";
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/{application_id}")
    public ResponseEntity resolveApplication(@PathVariable String application_id,
                                            @RequestBody Boolean approved) {
        log.info("Request to " + (approved ? "approve" : "deny") + " application " + application_id + " received");

        // Application resolving service call goes here

        return new ResponseEntity(HttpStatus.OK);
    }
}
