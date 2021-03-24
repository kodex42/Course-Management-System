package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.BusinessLogic.BusinessLogicHandlerFactory;
import com.comp3000.project.cms.BusinessLogic.CourseRegistrationBL;
import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.exception.CannotRegisterException;
import com.comp3000.project.cms.exception.FieldNotValidException;
import com.comp3000.project.cms.forms.CourseOfferingForm;
import com.comp3000.project.cms.services.CourseOffering.CourseOfferingCommandService;
import com.comp3000.project.cms.services.CourseOffering.CourseOfferingQueryService;
import com.comp3000.project.cms.services.Course.CourseQueryService;
import com.comp3000.project.cms.services.Term.TermQueryService;
import com.comp3000.project.cms.services.User.UserQueryService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityExistsException;
import javax.persistence.criteria.CriteriaBuilder;
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
    @Autowired
    private BusinessLogicHandlerFactory factory;
    private Handler<Pair<CourseOffering, User>> studentRegisteredCourseRelationshipHandler;


    private void populateOptions(Model model){
        model.addAttribute("courses", courseQueryService.getAll());
        model.addAttribute("terms", termQueryService.getAll());
        model.addAttribute("professors", userQueryService.getAllUsersOfType("PROFESSOR"));
    }

    @GetMapping("/redirect")
    public String courseOfferingRedirectHome(RedirectAttributes redirectAttributes) {
        return "redirect:/";
    }

    @GetMapping
    public String listCourseOfferings(Model model){
        log.info("List of course offerings requested");

        model.addAttribute("courseOfferings", courseOfferingQueryService.getAll());

        return "course_offerings";
    }

    @GetMapping(path= "/create")
    public String getCreationForm(@ModelAttribute CourseOfferingForm courseOfferingForm,
                                  Model model){
        log.info("Course offering creation form requested");

        populateOptions(model);

        return "create_course_offr";
    }

    @PostMapping
    public String createCourseOffering(@ModelAttribute @Valid CourseOfferingForm courseOfferingForm,
                                       BindingResult bindingResult,
                                       Model model){
        log.info("Request to create course offering received");

        if(!bindingResult.hasErrors()) {
            try {
                Integer courseOffrId = courseOfferingCommandService.createCourse(courseOfferingForm).getId();

                return "redirect:/course_offerings/" + courseOffrId;
            } catch (FieldNotValidException e) {
                bindingResult.rejectValue(e.getField(), e.getCode(), e.getMessage());
            } catch (EntityExistsException e) {
                bindingResult.reject("error.global", e.getMessage());
            }
        }

        populateOptions(model);

        return "create_course_offr";
    }

    @GetMapping("/{courseOffrId}")
    public String viewCourseOffering(@PathVariable Integer courseOffrId,
                                     Principal principal,
                                     Model model) {
        log.info("Course offering page of course offering " + courseOffrId + " requested");

        try{
            CourseOffering courseOffering = courseOfferingQueryService.getById(courseOffrId);
            User user = userQueryService.getByUsername(principal.getName());

            if(user.getAuthority().equals("STUDENT"))
                model.addAttribute("registered", CourseRegistrationBL.isRegistered(courseOffering, user));

            model.addAttribute("courseOffering", courseOffering);
            return "course_offering";
        }catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{courseOffrId}")
    public String dropCourseOffering(@PathVariable Integer courseOffrId,
                                     Principal principal,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        log.info("Request to drop course with id " + courseOffrId + " received");

        CourseOffering courseOffering;
        User student;

        try {
            courseOffering = courseOfferingQueryService.getById(courseOffrId);
            student = userQueryService.getByUsername(principal.getName());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        studentRegisteredCourseRelationshipHandler = factory.createDropCourseOfferingHandler();
        Status status = studentRegisteredCourseRelationshipHandler.handle(Pair.of(courseOffering, student));

        if (status.isSuccessful()) {
            return listCourseOfferings(model);
        } else {
            redirectAttributes.addFlashAttribute("status", status);
            return courseOfferingRedirectHome(redirectAttributes);
        }
    }

    @PostMapping("/{courseOffrId}/register")
    public String registerInCourseOffering(@PathVariable Integer courseOffrId,
                                           Principal principal){
        log.info("Registration request for course offering " + courseOffrId + " requested");

        try{
            CourseOffering courseOffering = courseOfferingQueryService.getById(courseOffrId);
            User student = userQueryService.getByUsername(principal.getName());

            courseOfferingCommandService.registerInCourseOffering(courseOffering, student);

            return "redirect:/course_offerings/" + courseOffering.getId();
        }catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch (CannotRegisterException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }




}
