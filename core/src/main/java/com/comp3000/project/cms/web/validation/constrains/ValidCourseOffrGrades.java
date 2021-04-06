package com.comp3000.project.cms.web.validation.constrains;

import com.comp3000.project.cms.web.validation.validators.CourseOffrGradesValidator;
import com.comp3000.project.cms.web.validation.validators.PrereqPreclValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CourseOffrGradesValidator.class})
@Documented
public @interface ValidCourseOffrGrades {

    String message() default "Invalid grade(s)";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
