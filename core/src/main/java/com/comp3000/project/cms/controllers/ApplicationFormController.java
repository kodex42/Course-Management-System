package com.comp3000.project.cms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationFormController {
    @GetMapping("/application-form")
    public String applicationForm(Model model) {

//        model.addAttribute("application", new Reg)
        return "application-form";
    }
}
