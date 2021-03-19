package com.comp3000.project.cms;

import com.comp3000.project.cms.DAC.UserType;
import com.comp3000.project.cms.components.CMS;
import com.comp3000.project.cms.repository.UserTypeRepository;
import com.comp3000.project.cms.repository.UserRepository;
import com.comp3000.project.cms.services.UserQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityDemoController {
    private static final Logger log = LoggerFactory.getLogger(SecurityDemoController.class);

    @Autowired
    private UserQueryService userQueryService;

    @GetMapping(path="/")
    public String viewIndex(Model model) {
        log.info("Request: index");

        Iterable<UserType> ut = userQueryService.loadAllUserTypes();
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
        model.addAttribute("users", userQueryService.loadAllUsers());
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
