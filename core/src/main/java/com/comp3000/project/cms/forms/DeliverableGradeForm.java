package com.comp3000.project.cms.forms;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

/* Read-Only POJO for controllers
{
        "professor_first_name" : "fname",
        "professor_last_name" : "lname",
        "course_name" : "cname",
        "deliverable_name" : "dname",
        "student_first_name" : "fname",
        "student_last_name" : "lname",
        "grade" : 100.0
}
*/
public class DeliverableGradeForm {
    @NotEmpty(message = "The First Name of the grading professor is required.")
    private String professor_first_name;
    @NotEmpty(message = "The Last Name of the grading professor is required.")
    private String professor_last_name;
    @NotEmpty(message = "Course not specified.")
    private String course_name;
    @NotEmpty(message = "Deliverable not specified.")
    private String deliverable_name;
    @NotEmpty(message = "The First Name of the student to be graded is required.")
    private String student_first_name;
    @NotEmpty(message = "The Last Name of the student to be graded is required.")
    private String student_last_name;
    @DecimalMin(value = "0", message = "Assigned grade cannot be negative.")
    @DecimalMax(value = "100", message = "Assigned grade cannot be greater than 100.")
    private float grade;

    public String getProfessor_first_name() {
        return professor_first_name;
    }

    public String getProfessor_last_name() {
        return professor_last_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getDeliverable_name() {
        return deliverable_name;
    }

    public String getStudent_first_name() {
        return student_first_name;
    }

    public String getStudent_last_name() {
        return student_last_name;
    }

    public float getGrade() {
        return grade;
    }
}
