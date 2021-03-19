package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.components.CMS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/*  Term

    Handles the following routes:
        PUT
            /term
*/
@Controller
@RequestMapping("/term")
public class TermController {
    private static final Logger log = LoggerFactory.getLogger(TermController.class);

    @Autowired
    private CMS cms;

    @GetMapping
    public String termRedirect() {
        return "redirect:/";
    }

    @PutMapping
    public String changeCurrentTerm(@DateTimeFormat(pattern = "yyyy-MM-dd") Date newTermDate) {
        log.info("Request to change current term received");

        cms.loadTermForDate(newTermDate);

        return termRedirect();
    }
}
