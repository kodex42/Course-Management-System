package com.comp3000.project.cms.web.controllers;

import com.comp3000.project.cms.BLL.*;
import com.comp3000.project.cms.DAL.Visitor.GradedStudentCountingVisitor;
import com.comp3000.project.cms.DAL.Visitor.Visitor;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.CourseOffrStudentGrade;
import com.comp3000.project.cms.DAO.Deliverable;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.exception.*;
import com.comp3000.project.cms.DAL.services.CourseOffering.CourseOfferingCommandService;
import com.comp3000.project.cms.DAL.services.CourseOffering.CourseOfferingQueryService;
import com.comp3000.project.cms.DAL.services.Course.CourseQueryService;
import com.comp3000.project.cms.DAL.services.Term.TermQueryService;
import com.comp3000.project.cms.DAL.services.User.UserQueryService;
import com.comp3000.project.cms.web.forms.CourseOfferingForm;
import com.comp3000.project.cms.web.forms.CourseOffrGradesForm;
import com.comp3000.project.cms.web.forms.FilterForm;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/course_offerings")
public class CourseOfferingController {

    private static final Logger log = LoggerFactory.getLogger(CourseOfferingController.class);

    @Autowired
    private CourseOfferingCommandService courseOfferingCommandService;
    @Autowired
    private CourseOfferingQueryService courseOfferingQueryService;
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private TermQueryService termQueryService;
    @Autowired
    private CourseQueryService courseQueryService;


    private void populateModelCreate(Model model) {
        model.addAttribute("courses", courseQueryService.getAll());
        model.addAttribute("terms", termQueryService.getAll());
        model.addAttribute("professors", userQueryService.getAllUsersOfType("PROFESSOR"));
    }

    private void populateModelView(Model model, Integer courseOffrId, Principal principal, CourseOffrGradesForm courseOffrGradesForm) throws NotFoundException{
        CourseOffering courseOffering = courseOfferingQueryService.getById(courseOffrId);
        User user = userQueryService.getByUsername(principal.getName());

        Visitor gradedStudents = new GradedStudentCountingVisitor();
        courseOffering.getDeliverables().forEach((Deliverable d) -> d.accept(gradedStudents));

        model.addAttribute("gradedStudents", gradedStudents);
        if (user.getAuthority().equals("STUDENT")) {
            model.addAttribute("registered", CourseRegistrationBL.isRegistered(courseOffering, user));
        }
        model.addAttribute("courseOffering", courseOffering);
        model.addAttribute("user", user);

        courseOffrGradesForm.getStudentGrades().clear();
        for(CourseOffrStudentGrade studentGrade: courseOffering.getStudentGrades()){
            courseOffrGradesForm.getStudentGrades().put(studentGrade.getStudent().getId(), studentGrade.getGrade());
        }
    }

    @GetMapping("/student_redirect")
    public String studentRedirect() {
        return "redirect:/student";
    }

    @GetMapping("/listings_redirect")
    public String listingsRedirect() {
        return "redirect:/course_offerings";
    }

    private String courseOffrRedirect(Integer courseOffrId) { return "redirect:/course_offerings/" + courseOffrId; }

    @GetMapping
    public String listCourseOfferings(@ModelAttribute FilterForm filterForm,
                                      Principal principal,
                                      Model model){
        log.info("List of course offerings requested");

        filterForm.setUsername(principal.getName());
        model.addAttribute("courseOfferingListings", courseOfferingQueryService.getAllWithFilters(filterForm));
        model.addAttribute("terms", termQueryService.getAll());

        return "course_offerings";
    }

    @PostMapping("/filter")
    public String listCourseOfferingsWithFilter(@ModelAttribute FilterForm filterForm,
                                                Principal principal,
                                                Model model) {
        return listCourseOfferings(filterForm, principal, model);
    }

    @GetMapping(path= "/create")
    public String getCreationForm(@ModelAttribute CourseOfferingForm courseOfferingForm,
                                  Model model) {
        log.info("Course offering creation form requested");

        populateModelCreate(model);

        return "create_course_offr";
    }

    @PostMapping
    public String createCourseOffering(@ModelAttribute @Valid CourseOfferingForm courseOfferingForm,
                                       BindingResult bindingResult,
                                       Model model) {
        log.info("Request to create course offering received");

        if (!bindingResult.hasErrors()) {
            try {
                Integer courseOffrId = courseOfferingCommandService.createCourseOffering(courseOfferingForm).getId();

                return "redirect:/course_offerings/" + courseOffrId;
            } catch (FieldNotValidException e) {
                bindingResult.rejectValue(e.getField(), e.getCode(), e.getMessage());
            } catch (EntityExistsException e) {
                bindingResult.reject("error.global", e.getMessage());
            }
        }

        populateModelCreate(model);

        return "create_course_offr";
    }

    @GetMapping("/{courseOffrId}")
    public String viewCourseOffering(@PathVariable Integer courseOffrId,
                                     @ModelAttribute CourseOffrGradesForm courseOffrGradesForm,
                                     Principal principal,
                                     Model model) {
        log.info("Course offering page of course offering " + courseOffrId + " requested");

        try {
            populateModelView(model,courseOffrId,principal, courseOffrGradesForm);
            return "course_offering";
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{courseOffrId}")
    public String dropCourseOffering(@PathVariable Integer courseOffrId,
                                     Principal principal,
                                     Model model) {
        log.info("Request to drop course with id " + courseOffrId + " received");

        try {
            courseOfferingCommandService.dropCourseOffering(courseOffrId, principal.getName());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (CannotDropException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        return studentRedirect();
    }

    @DeleteMapping("/{courseOffrId}")
    public String removeCourseOffering(@PathVariable Integer courseOffrId,
                                       Principal principal,
                                       Model model) {
        log.info("Request to remove course offering with id " + courseOffrId + " received");

        try {
            courseOfferingCommandService.delete(courseOffrId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (CannotDeleteException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return listingsRedirect();
    }

    @PostMapping("/{courseOffrId}/register")
    public String registerInCourseOffering(@PathVariable Integer courseOffrId,
                                           Principal principal,
                                           Model model){
        log.info("Registration request for course offering " + courseOffrId + " requested");

        try {
            courseOfferingCommandService.registerInCourseOffering(courseOffrId, principal.getName());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (CannotRegisterException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        return courseOffrRedirect(courseOffrId);
    }

    @PutMapping("/{courseOffrId}/grades")
    public String submitCourseOffertingGrades(@PathVariable Integer courseOffrId,
                                              @ModelAttribute @Valid CourseOffrGradesForm courseOffrGradesForm,
                                              BindingResult bindingResult,
                                              Principal principal,
                                              Model model){
        log.info("Grade submission request for course offering " + courseOffrId);

        try {
            if (!bindingResult.hasErrors()) {
                try {
                    courseOfferingCommandService.submitCourseOffrGrades(courseOffrId, courseOffrGradesForm);
                }catch (FieldNotValidException e){
                    bindingResult.rejectValue(e.getField(), e.getCode(), e.getMessage());
                }catch (CannortSubmitCourseOffrGradesException e){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
                }

                return courseOffrRedirect(courseOffrId);
            }

            populateModelView(model, courseOffrId, principal, courseOffrGradesForm);
            return "course_offering";

        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
