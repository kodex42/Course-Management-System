package com.comp3000.project.cms.web.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CourseOfferingForm extends Form {
    @NotNull(message = "Course id is required.")
    private Integer course;
    @NotNull(message = "Term id is required.")
    private Integer term;
    @NotNull(message = "Professor id is required.")
    private Integer professor;
    @NotNull(message = "Capacity is required.")
    @Min(value = 25, message = "Capacity can be no lower than 25 students.")
    @Max(value = 500, message = "Capacity can be no greater than 500 students.")
    private Integer capacity;

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getProfessor() {
        return professor;
    }

    public void setProfessor(Integer professor) {
        this.professor = professor;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
