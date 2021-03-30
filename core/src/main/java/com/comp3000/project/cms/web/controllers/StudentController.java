package com.comp3000.project.cms.web.controllers;

import com.comp3000.project.cms.web.forms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*  StudentController

    Handles the following routes:
        GET
            /students
            /students/{student_username}
            /students/{student_username}/courses
            /students/{student_username}/courses/grades
            /students/{student_username}/courses/{course_name}/deliverables/grades
        POST
            /students/{student_username}/courses
        DELETE
            /students/{student_username}
            /students/{student_username}/courses/{course_name}
        PUT
            /students/{student_username}/courses/{course_name}/grades
            /students/{student_username}/courses/{course_name}/deliverables/{deliverable_name}/submissions
            /students/{student_username}/courses/{course_name}/deliverables/{deliverable_name}/grades
*/
@Controller
@RequestMapping("/students")
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @GetMapping
    public String listStudents(Model model) {
        log.info("Student list requested");

        // Student list retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "students";
    }

    @GetMapping("/{student_username}")
    public String viewStudent(@PathVariable String student_username,
                              Model model) {
        log.info("Student page for " + student_username + " requested");

        // Student data retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "student";
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/{student_username}")
    public ResponseEntity removeStudent(@PathVariable String student_username) {
        log.info("Request to delete student " + student_username + " received");

        // Student removal service call goes here

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{student_username}/courses")
    public String listRegisteredCourses(@PathVariable String student_username,
                                        Model model) {
        log.info("Registered courses page for " + student_username + " requested");

        // Student registered courses list retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "registered-courses";
    }

    @PostMapping("/{student_username}/courses")
    public ResponseEntity<CourseRegistrationForm> registerForCourse(@PathVariable String student_username,
                                                                    @Valid @RequestBody CourseRegistrationForm courseRegistrationForm) {
        log.info("Request to register student " + student_username + " for a course");

        // Student register for course service call goes here

        return new ResponseEntity<>(courseRegistrationForm, HttpStatus.OK);
    }

    @GetMapping("/{student_username}/courses/grades")
    public String listCourseGrades(@PathVariable String student_username,
                                   Model model) {
        log.info("Course grades page for " + student_username + " requested");

        // Student course grades list retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "course-grades";
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/{student_username}/courses/{course_name}")
    public ResponseEntity dropCourse(@PathVariable("student_username") String student_username,
                                     @PathVariable("course_name") String course_name) {
        log.info("Request to drop student " + student_username + " from course " + course_name);

        // Student drop course service call goes here

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{student_username}/courses/{course_name}/grades")
    public ResponseEntity<CourseGradeForm> submitCourseGrade(@PathVariable("student_username") String student_username,
                                                             @PathVariable("course_name") String course_name,
                                                             @Valid @RequestBody CourseGradeForm courseGradeForm) {
        log.info("Request to submit course grade for student " + student_username + " in course " + course_name);

        // Course grade submission service call goes here

        return new ResponseEntity<>(courseGradeForm, HttpStatus.OK);
    }

    @GetMapping("/{student_username}/courses/{course_name}/deliverables/grades")
    public String listDeliverableGrades(@PathVariable("student_username") String student_username,
                                        @PathVariable("course_name") String course_name,
                                        Model model) {
        log.info("Deliverable grades page for student " + student_username + " in course " + course_name + " requested");

        // Student deliverable grades list retrieval service call goes here

        // Add data to model
        model.addAttribute("access_level", "student");

        return "deliverable-grades";
    }

    @PutMapping("/{student_username}/courses/{course_name}/deliverables/{deliverable_name}/submissions")
    public ResponseEntity<DeliverableSubmissionForm> submitDeliverable(@PathVariable("student_username") String student_username,
                                                                       @PathVariable("course_name") String course_name,
                                                                       @PathVariable("deliverable_name") String deliverable_name,
                                                                       @Valid @RequestBody DeliverableSubmissionForm deliverableSubmissionForm) {
        log.info("Request to submit for deliverable " + deliverable_name + " by student " + student_username + " in course " + course_name);

        // Student deliverable submission service call goes here

        return new ResponseEntity<>(deliverableSubmissionForm, HttpStatus.OK);
    }

    @PutMapping("/{student_username}/courses/{course_name}/deliverables/{deliverable_name}/grades")
    public ResponseEntity<DeliverableGradeForm> submitDeliverableGrade(@PathVariable("student_username") String student_username,
                                                                       @PathVariable("course_name") String course_name,
                                                                       @PathVariable("deliverable_name") String deliverable_name,
                                                                       @Valid @RequestBody DeliverableGradeForm deliverableGradeForm) {
        log.info("Request to submit grade for deliverable " + deliverable_name + " for student " + student_username + " in course " + course_name);

        // Student deliverable grade submission service call goes here

        return new ResponseEntity<>(deliverableGradeForm, HttpStatus.OK);
    }
}
