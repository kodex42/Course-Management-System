package com.comp3000.project.cms.web.controllers;

import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.DAO.UserType;
import com.comp3000.project.cms.components.CMS;
import com.comp3000.project.cms.DAL.services.User.UserQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/*  HomeController

    Handles the following routes:
        GET
            /
            /index
            /login
            /admin
            /student
            /professor
*/
@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private CMS cms;

    @GetMapping({"/", "/index"})
    public String viewIndex(Model model) {
        log.info("Request: index");

        Iterable<UserType> ut = userQueryService.getAllUserTypes();

        // Add data to model
        model.addAttribute("term", cms.getCurrentTerm());
        model.addAttribute("currentDate", cms.getCurrentTime());
        model.addAttribute("userTypes", ut);

        return "index";
    }

    @GetMapping("/login")
    public String viewLoginPage(Model model) {
        log.info("Request: login");

        return "login";
    }

    @GetMapping("/admin")
    public String viewAdmin(Model model) {
        log.info("Request: admin");
        model.addAttribute("users", userQueryService.getAllUsers());
        return "admin";
    }

    @GetMapping("/student")
    public String viewStudent(Model model,
                              Principal principal) {
        log.info("Request: student");

        User student = userQueryService.getByUsername(principal.getName());
        model.addAttribute("user", student);

        return "student";
    }

    @GetMapping("/professor")
    public String viewProfessor(Model model,
                                Principal principal) {
        log.info("Request: professor");

        User professor = userQueryService.getByUsername(principal.getName());
        model.addAttribute("user", professor);

        return "professor";
    }
}
