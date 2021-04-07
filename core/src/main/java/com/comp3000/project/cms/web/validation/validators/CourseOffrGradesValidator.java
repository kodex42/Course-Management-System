package com.comp3000.project.cms.web.validation.validators;

import com.comp3000.project.cms.web.forms.CourseOffrGradesForm;
import com.comp3000.project.cms.web.validation.constrains.ValidCourseOffrGrades;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CourseOffrGradesValidator implements ConstraintValidator<ValidCourseOffrGrades, CourseOffrGradesForm> {

    @Override
    public boolean isValid(CourseOffrGradesForm courseOffrGradesForm, ConstraintValidatorContext constraintValidatorContext) {
        if(courseOffrGradesForm == null) return true;

        return courseOffrGradesForm.getStudentGrades().entrySet().stream().noneMatch(e ->
                e.getValue() != null && (e.getValue() > 100 || e.getValue() < 0));
    }

    @Override
    public void initialize(ValidCourseOffrGrades constraintAnnotation) { }
}
