package com.comp3000.project.cms.web.forms;


import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/* Read-Only POJO for controllers
{
        "course_name" : "cname",
        "student_username" : "uname",
        "grade" : 100.0
}
*/
public class CourseGradeForm extends Form {
    @NotEmpty(message = "Course not specified.")
    private String course_name;
    @NotEmpty(message = "The username of the student to be graded is required.")
    private String student_username;
    @DecimalMin(value = "0", message = "Assigned grade cannot be negative.")
    @DecimalMax(value = "100", message = "Assigned grade cannot be greater than 100.")
    private BigDecimal grade;

    public String getCourse_name() {
        return course_name;
    }

    public String getStudent_username() {
        return student_username;
    }

    public BigDecimal getGrade() {
        return grade;
    }
}
