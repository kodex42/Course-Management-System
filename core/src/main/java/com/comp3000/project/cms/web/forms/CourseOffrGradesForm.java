package com.comp3000.project.cms.web.forms;

import com.comp3000.project.cms.web.validation.constrains.ValidCourseOffrGrades;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@ValidCourseOffrGrades
public class CourseOffrGradesForm {
    @NotNull(message = "Student grades are required.")
    private Map<Integer, Float> studentGrades = new HashMap<>();

    public Map<Integer, Float> getStudentGrades() {
        return studentGrades;
    }

    public void setStudentGrades(Map<Integer, Float> studentGrades) {
        this.studentGrades = studentGrades;
    }
}
