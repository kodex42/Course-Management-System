package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.services.CourseOfferingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@Controller
@RequestMapping("/course_offerings/")
public class CourseOfferingController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private CourseOfferingService courseOfferingService;

}
