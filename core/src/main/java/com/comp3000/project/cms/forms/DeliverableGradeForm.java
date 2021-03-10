package com.comp3000.project.cms.forms;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/* Read-Only POJO for controllers
{
        "course_name" : "cname",
        "deliverable_name" : "dname",
        "student_username" : "uname",
        "grade" : 100.0
}
*/
public class DeliverableGradeForm extends Form {
    @NotEmpty(message = "Course not specified.")
    private String course_name;
    @NotEmpty(message = "Deliverable not specified.")
    private String deliverable_name;
    @NotEmpty(message = "The username of the student to be graded is required.")
    private String student_username;
    @DecimalMin(value = "0", message = "Assigned grade cannot be negative.")
    @DecimalMax(value = "100", message = "Assigned grade cannot be greater than 100.")
    private BigDecimal grade;

    public String getCourse_name() {
        return course_name;
    }

    public String getDeliverable_name() {
        return deliverable_name;
    }

    public String getStudent_username() {
        return student_username;
    }

    public BigDecimal getGrade() {
        return grade;
    }
}
