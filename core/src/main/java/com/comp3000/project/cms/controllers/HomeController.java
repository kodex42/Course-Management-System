package com.comp3000.project.cms.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/*  HomeController

    Handles the following routes:
        GET
            /index
*/
@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/index")
    public String getHome(@RequestHeader(name = "username", defaultValue = "Your Name Here") String username,
                          Model model) {
        log.info("Request: index");

        // Add data to model
        model.addAttribute("access_level", "student");
        model.addAttribute("name", username);

        return "index";
    }
}
