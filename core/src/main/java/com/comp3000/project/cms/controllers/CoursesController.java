package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.forms.CourseForm;
import com.comp3000.project.cms.forms.CourseGradeForm;
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
@Validated // Used for validating collections
@Controller
@RequestMapping("/courses")
public class CoursesController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping
    public String listCourses(Model model) {
        log.info("Course list requested");

        // Course list retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "courses";
    }

    @PostMapping
    public ResponseEntity<CourseForm> addCourse(@Valid @RequestBody CourseForm courseForm) {
        log.info("Request to add course received");

        // Course adding service call goes here

        return new ResponseEntity<>(courseForm, HttpStatus.OK);
    }

    @GetMapping("/{course_name}")
    public String viewCourse(@PathVariable String course_name,
                             Model model) {
        log.info("Course page of " + course_name + " requested");

        // Course data retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "course";
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/{course_name}")
    public ResponseEntity removeCourse(@PathVariable String course_name) {
        log.info("Request to remove course " + course_name + " received");

        // Course removal service call goes here

        return new ResponseEntity(HttpStatus.OK);
    }

    @SuppressWarnings("rawtypes")
    @PutMapping("/{course_name}/grades")
    public ResponseEntity<List> submitBulkCourseGrades(@PathVariable String course_name,
                                                       @RequestBody List<@Valid CourseGradeForm> courseGradeForms) {
        log.info("Request to submit bulk grades for course " + course_name + " received");

        // Course grade submission service call goes here

        return new ResponseEntity<>(courseGradeForms, HttpStatus.OK);
    }
}
