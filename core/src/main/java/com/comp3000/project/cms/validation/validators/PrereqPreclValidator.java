package com.comp3000.project.cms.validation.validators;

import com.comp3000.project.cms.forms.CourseForm;
import com.comp3000.project.cms.validation.constrains.ValidPrereqPrecl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class PrereqPreclValidator implements ConstraintValidator<ValidPrereqPrecl, CourseForm> {

    @Override
    public boolean isValid(CourseForm courseForm, ConstraintValidatorContext constraintValidatorContext) {
        if(courseForm == null) return true;

        List<String> prerequisites = courseForm.getPrerequisites();
        List<String> preclusions = courseForm.getPreclusions();

        return !(preclusions.stream().anyMatch((precl) -> precl.equals(courseForm.getName())) ||
                prerequisites.stream().anyMatch((prereq) -> prereq.equals(courseForm.getName())) ||
                prerequisites.stream().distinct().anyMatch(preclusions::contains));
    }

    @Override
    public void initialize(ValidPrereqPrecl constraintAnnotation) { }
}
