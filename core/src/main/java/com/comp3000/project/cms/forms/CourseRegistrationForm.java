package com.comp3000.project.cms.forms;

import javax.validation.constraints.NotEmpty;

/* Read-Only POJO for controllers
{
        "course_name" : "cname"
}
*/
public class CourseRegistrationForm {
    @NotEmpty(message = "Course not specified.")
    private String course_name;

    public String getCourse_name() {
        return course_name;
    }
}
