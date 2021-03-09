package com.comp3000.project.cms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityDemoController {

    private static final Logger log = LoggerFactory.getLogger(SecurityDemoController.class);

    @GetMapping("/")
    public String viewIndex() {
        log.info("Request: index");

        return "index";
    }

    @GetMapping("/admin")
    public String viewAdmin() {
        log.info("Request: admin");

        return "admin";
    }

    @GetMapping("/student")
    public String viewStudent() {
        log.info("Request: student");

        return "student";
    }

    @GetMapping("/professor")
    public String viewProfessor() {
        log.info("Request: professor");

        return "professor";
    }


}
