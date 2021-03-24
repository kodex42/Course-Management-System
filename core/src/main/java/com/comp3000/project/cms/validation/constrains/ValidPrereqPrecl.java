package com.comp3000.project.cms.validation.constrains;

import com.comp3000.project.cms.validation.validators.PrereqPreclValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PrereqPreclValidator.class})
@Documented
public @interface ValidPrereqPrecl {

    String message() default "Invalid prerequisites / preclusions";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
