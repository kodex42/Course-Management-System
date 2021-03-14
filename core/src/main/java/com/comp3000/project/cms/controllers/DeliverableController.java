package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.forms.DeliverableForm;
import com.comp3000.project.cms.forms.CourseGradeForm;
import com.comp3000.project.cms.forms.DeliverableGradeForm;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/*  DeliverableController

    Handles the following routes:
        GET
            /courses/{course_name}/deliverables
            /courses/{course_name}/deliverables/{deliverable_name}
        POST
            /courses/{course_name}/deliverables
        DELETE
            /courses/{course_name}/deliverables/{deliverable_name}
        PUT
            /courses/{course_name}/deliverables/{deliverable_name}
            /courses/{course_name}/deliverables/{deliverable_name}/grades
*/
@Validated // Used for validating collections
@Controller
@RequestMapping("/courses/{course_name}/deliverables")
public class DeliverableController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping
    public String listDeliverables(@PathVariable String course_name,
                                   Model model) {
        log.info("Deliverables list requested");

        // Deliverable list retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "deliverables";
    }

    @PostMapping
    public ResponseEntity<DeliverableForm> addDeliverable(@PathVariable String course_name,
                                                          @Valid @RequestBody DeliverableForm deliverableForm) {
        log.info("Request to add deliverable received");

        // Deliverable adding service call goes here

        return new ResponseEntity<>(deliverableForm, HttpStatus.OK);
    }

    @GetMapping("/{deliverable_name}")
    public String viewDeliverable(@PathVariable("course_name") String course_name,
                                  @PathVariable("deliverable_name") String deliverable_name,
                                  Model model) {
        log.info("Deliverable page for " + deliverable_name + " from " + course_name + " requested");

        // Deliverable data retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "deliverable";
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/{deliverable_name}")
    public ResponseEntity removeDeliverable(@PathVariable("course_name") String course_name,
                                            @PathVariable("deliverable_name") String deliverable_name) {
        log.info("Request to delete deliverable " + deliverable_name + " from " + course_name + " received");

        // Deliverable removal service call goes here

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{deliverable_name}")
    public ResponseEntity<DeliverableForm> modifyDeliverable(@PathVariable("course_name") String course_name,
                                                             @PathVariable("deliverable_name") String deliverable_name,
                                                             @Valid @RequestBody DeliverableForm deliverableForm) {
        log.info("Request to modify deliverable " + deliverable_name + " from " + course_name + " received");

        // Deliverable modification service call goes here

        return new ResponseEntity<>(deliverableForm, HttpStatus.OK);
    }

    @SuppressWarnings("rawtypes")
    @PutMapping("/{deliverable_name}/grades")
    public ResponseEntity<List> submitBulkDeliverableGrades(@PathVariable("course_name") String course_name,
                                                            @PathVariable("deliverable_name") String deliverable_name,
                                                            @RequestBody List<@Valid DeliverableGradeForm> deliverableGradeForms) {
        log.info("Request to submit bulk grades for deliverable " + deliverable_name + " from " + course_name + " received");

        // Deliverable grade submission service call goes here

        return new ResponseEntity<>(deliverableGradeForms, HttpStatus.OK);
    }
}