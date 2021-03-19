package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.DAC.Season;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.components.CMS;
import com.comp3000.project.cms.forms.TermForm;
import com.comp3000.project.cms.services.SeasonQuerySevice;
import com.comp3000.project.cms.services.Term.TermCommandService;
import com.comp3000.project.cms.services.Term.TermQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.Optional;

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

    @Autowired
    private TermCommandService termCommandService;
    @Autowired
    private SeasonQuerySevice seasonQueryService;

    @GetMapping(path="/rdr/")
    public String termRedirect() {
        return "redirect:/";
    }

    @PostMapping
    public RedirectView createTerm(@ModelAttribute TermForm term) {
        try {
            Optional<Season> seasonQuery = this.seasonQueryService.getById(term.getSeasonId());
            Season season = seasonQuery.orElseThrow();

            Term newTerm = term.toObject();
            newTerm.setSeason(season);

            this.termCommandService.createTerm(newTerm);
        } catch (Exception e) {
            log.error(e.toString());
        }

        return new RedirectView("/admin");
    }

    @GetMapping("/create")
    public String createTermGet(Model model) {
        log.info("Term creation page requested");

        model.addAttribute("term", new TermForm());
        model.addAttribute("seasons", this.seasonQueryService.getAll());
        return "create_term";
    }


    @PutMapping
    public String changeCurrentTerm(@DateTimeFormat(pattern = "yyyy-MM-dd") Date newTermDate) {
        log.info("Request to change current term received");

        cms.loadTermForDate(newTermDate);

        return termRedirect();
    }
}
