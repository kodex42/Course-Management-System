package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.DAC.Course;
import com.comp3000.project.cms.exception.FieldNotValidException;
import com.comp3000.project.cms.forms.CourseForm;
import com.comp3000.project.cms.forms.CourseGradeForm;
import com.comp3000.project.cms.services.CourseService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.lang.model.UnknownEntityException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/*  CoursesController

    Handles the following routes:
        GET
            /courses
            /courses/{course_name}
        POST
            /courses
        DELETE
            /courses/{course_name}
        PUT
            /courses/{course_name}/grades
*/

@Controller
@RequestMapping("/courses")
public class CoursesController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private CourseService courseService;

    @GetMapping
    public String listCourses(Model model) {
        log.info("Course list requested");

        model.addAttribute("courses", courseService.getAll());

        return "courses";
    }

    @GetMapping(path= "/create")
    public String getCreationForm(@ModelAttribute CourseForm courseForm) {
        log.info("Course creation form requested");

        return "createCourse";
    }

    @PostMapping(path = "/create", params = {"save"})
    public String createCourse(@ModelAttribute @Valid CourseForm courseForm, BindingResult bindingResult) {
        log.info("Request to create course received");

        if(bindingResult.hasErrors())
            return "createCourse";

        try{
            Integer courseId = courseService.createCourse(courseForm).getId();

            return "redirect:/courses/" + courseId;
        }catch (FieldNotValidException e){
            bindingResult.rejectValue(e.getField(), e.getCode(), e.getMessage());

            return "createCourse";
        }
    }

    @PostMapping(path = "/create", params = {"addPrereq"})
    public String addPrereq(@ModelAttribute CourseForm courseForm, BindingResult bindingResult) {
        log.info("Request to add prerequisite received");
        courseForm.getPrerequisites().add("");
        return "createCourse";
    }

    @PostMapping(path = "/create", params = {"removePrereq"})
    public String removePrereq(@RequestParam("removePrereq") Integer idx, @ModelAttribute CourseForm courseForm, BindingResult bindingResult) {
        log.info("Request to remove prerequisite received");
        courseForm.getPrerequisites().remove(idx.intValue());
        return "createCourse";
    }

    @PostMapping(path = "/create", params = {"addPrecl"})
    public String addPrecl(@ModelAttribute CourseForm courseForm, BindingResult bindingResult) {
        log.info("Request to add preclusion course received");
        courseForm.getPreclusions().add("");
        return "createCourse";
    }

    @PostMapping(path = "/create", params = {"removePrecl"})
    public String removePrecl(@RequestParam("removePrecl") Integer idx, @ModelAttribute CourseForm courseForm, BindingResult bindingResult) {
        log.info("Request to remove preclusion received");
        courseForm.getPreclusions().remove(idx.intValue());
        return "createCourse";
    }

    @GetMapping("/{courseId}")
    public String viewCourse(@PathVariable Integer courseId, Model model) {
        log.info("Course page of course " + courseId + " requested");

        try{
            Course course = courseService.getById(courseId);

            model.addAttribute("course", course);
            return "course";
        }catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{courseId}")
    public String removeCourse(@PathVariable Integer courseId) {
        log.info("Deletion of course " + courseId + " requested");

        try{
            courseService.removeById(courseId);

            return "redirect:/courses";
        }catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}
