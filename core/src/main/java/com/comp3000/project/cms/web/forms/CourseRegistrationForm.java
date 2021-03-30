package com.comp3000.project.cms.web.forms;

import javax.validation.constraints.NotEmpty;

/* Read-Only POJO for controllers
{
        "course_name" : "cname"
}
*/
public class CourseRegistrationForm extends Form {
    @NotEmpty(message = "Course not specified.")
    private String course_name;

    public String getCourse_name() {
        return course_name;
    }
}
