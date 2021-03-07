package com.comp3000.project.cms.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*  ProfessorController

    Handles the following routes:
        GET
            /professors
            /professors/{professor_username}
            /professors/{professor_username}/courses
        DELETE
            /professors/{professor_username}
*/
@Controller
@RequestMapping("/professors")
public class ProfessorController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping
    public String listProfessors(Model model) {
        log.info("Professor list requested");

        // Professor list retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "professors";
    }

    @GetMapping("/{professor_username}")
    public String viewProfessor(@PathVariable String professor_username,
                                Model model) {
        log.info("Professor page for professor " + professor_username + " requested");

        // Professor data retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "professor";
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/{professor_username}")
    public ResponseEntity removeProfessor(@PathVariable String professor_username) {
        log.info("Request to delete professor " + professor_username + " received");

        // Professor removal service call goes here

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{professor_username}/courses")
    public String listAssignedCourses(@PathVariable String professor_username,
                                      Model model) {
        log.info("Course list for courses assigned to professor " + professor_username);

        // Professor assigned course list retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "assigned-courses";
    }
}
