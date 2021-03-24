package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.Deliverable;
import com.comp3000.project.cms.forms.DeliverableForm;
import com.comp3000.project.cms.forms.DeliverableGradeForm;
import com.comp3000.project.cms.services.CourseOffering.CourseOfferingQueryService;
import com.comp3000.project.cms.services.Deliverable.DeliverableCommandService;
import com.comp3000.project.cms.services.StorageService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
            /courses-offerings/{id}/deliverables/create
            /courses-offerings/{id}/deliverables/{deliverable_name}
        POST
            /courses-offerings/{id}/deliverables
        DELETE
            /courses-offerings/{id}/deliverables/{deliverable_name}
        PUT
            /courses-offerings/{id}/deliverables/{deliverable_name}
            /courses-offerings/{id}/deliverables/{deliverable_name}/grades
*/
@Validated // Used for validating collections
@Controller
@RequestMapping("/courses-offerings/{courseOffrId}/deliverables")
public class DeliverableController {

    private static final Logger log = LoggerFactory.getLogger(DeliverableController.class);

    @Autowired
    private StorageService storageService;

    @Autowired
    private CourseOfferingQueryService courseOfferingQueryService;

    @Autowired
    private DeliverableCommandService deliverableCommandService;

    @GetMapping("/create")
    public String createDeliverable(@PathVariable Integer courseOffrId, Model model) {
        try {
            CourseOffering courseOffering = courseOfferingQueryService.getById(courseOffrId);

            DeliverableForm form = new DeliverableForm();
            form.setCourseOffr(courseOffering);

            model.addAttribute("deliverable", form);
        } catch (NotFoundException e) {
            log.error(e.toString());
        }

        return "create_course_deliverable";
    }

    @GetMapping
    public String listDeliverables(@PathVariable String course_name,
                                   Model model) {
        log.info("Deliverables list requested");

        // Deliverable list retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "deliverables";
    }

    @PostMapping()
    public String addDeliverable(Model model, @ModelAttribute DeliverableForm deliverable,
                                 @PathVariable Integer courseOffrId) {
        log.info("Request to add deliverable received");

        try {
            Deliverable delObj = deliverable.toObject();
            CourseOffering courseOffering = courseOfferingQueryService.getById(courseOffrId);
            delObj.setCourseOffering(courseOffering);
            delObj.setAuthor(courseOffering.getProfessor());

            if (deliverable.getFile() != null) {
                storageService.save(deliverable.getFile());
                delObj.setFilename(deliverable.getFile().getOriginalFilename());
            }

            deliverableCommandService.create(delObj);

            Status<DeliverableForm> status = Status.ok(deliverable);

            DeliverableForm form = new DeliverableForm();
            form.setCourseOffr(courseOffering);
            model.addAttribute("status", status);
            model.addAttribute("deliverable", form);
        } catch (Exception e) {
            log.error(e.toString());
        }


        return "create_course_deliverable";
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
