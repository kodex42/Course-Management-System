package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.DAC.Course;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.exception.FieldNotValidException;
import com.comp3000.project.cms.forms.CourseOfferingForm;
import com.comp3000.project.cms.services.CourseOffering.CourseOfferingCommandService;
import com.comp3000.project.cms.services.CourseOffering.CourseOfferingQueryService;
import com.comp3000.project.cms.services.Course.CourseQueryService;
import com.comp3000.project.cms.services.Term.TermQueryService;
import com.comp3000.project.cms.services.UserQueryService;
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
import java.util.List;

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

    @ModelAttribute("courses")
    public List<Course> populateCourses(){
        return (List<Course>) courseQueryService.getAll();
    }

    @ModelAttribute("terms")
    public List<Term> populateTerms(){
        return (List<Term>)termQueryService.getAll();
    }

    @ModelAttribute("professors")
    public List<User> populateProfessors(){
        return (List<User>)userQueryService.loadAllUsersOfType("PROFESSOR");
    }

    @GetMapping
    public String listCourseOfferings(Model model){
        log.info("List of course offerings requested");

        model.addAttribute("courseOfferings", courseOfferingQueryService.getAll());

        return "course_offerings";
    }

    @GetMapping(path= "/create")
    public String getCreationForm(@ModelAttribute CourseOfferingForm courseOfferingForm){
        log.info("Course offering creation form requested");

        return "create_course_offr";
    }

    @PostMapping
    public String createCourseOffering(@ModelAttribute @Valid CourseOfferingForm courseOfferingForm, BindingResult bindingResult, Model model){
        log.info("Request to create course offering received");

        if(bindingResult.hasErrors())
            return "create_course_offr";

        try {
            Integer courseOffrId = courseOfferingCommandService.createCourse(courseOfferingForm).getId();

            return "redirect:/course_offerings/" + courseOffrId;
        }catch (FieldNotValidException e){
            bindingResult.rejectValue(e.getField(), e.getCode(), e.getMessage());
        }catch (EntityExistsException e){
            bindingResult.reject("error.global", e.getMessage());
        }

        return "create_course_offr";
    }

    @GetMapping("/{courseOffrId}")
    public String viewCourse(@PathVariable Integer courseOffrId, Model model) {
        log.info("Course offering page of course " + courseOffrId + " requested");

        try{
            CourseOffering courseOffering = courseOfferingQueryService.getById(courseOffrId);

            model.addAttribute("courseOffering", courseOffering);
            return "course_offering";
        }catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }




}
