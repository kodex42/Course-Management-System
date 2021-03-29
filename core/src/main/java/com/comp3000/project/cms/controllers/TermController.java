package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.BusinessLogic.BusinessLogicHandlerFactory;
import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.Season;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.components.CMS;
import com.comp3000.project.cms.exception.CannotCreateException;
import com.comp3000.project.cms.forms.TermForm;
import com.comp3000.project.cms.services.SeasonQueryService;
import com.comp3000.project.cms.services.Term.TermCommandService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.time.DateTimeException;
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
    private SeasonQueryService seasonQueryService;

    private String termRedirect() {
        return "redirect:/";
    }

    @PostMapping
    public String createTerm(Model model, @ModelAttribute TermForm term) {
        try {
            termCommandService.createTerm(term);
            model.addAttribute("seasons", this.seasonQueryService.getAll());
            model.addAttribute("term", term);
            model.addAttribute("status", Status.ok(0));
        } catch (CannotCreateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        return "create_term";
    }

    @GetMapping("/create")
    public String createTermGet(Model model) {
        log.info("Term creation page requested");

        model.addAttribute("term", new TermForm());
        model.addAttribute("seasons", this.seasonQueryService.getAll());
        model.addAttribute("status", null);
        return "create_term";
    }


    @PutMapping
    public String changeCurrentTerm(@DateTimeFormat(pattern = "yyyy-MM-dd") Date newTermDate) {
        log.info("Request to change current term received");

        try {
            cms.loadTermForDate(new java.sql.Date(newTermDate.getTime()));

            return termRedirect();
        }catch (DateTimeException e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }
}
