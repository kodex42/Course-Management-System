package com.comp3000.project.cms.converters;

import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.exception.FieldNotValidException;
import com.comp3000.project.cms.forms.CourseOfferingForm;
import com.comp3000.project.cms.services.Course.CourseQueryService;
import com.comp3000.project.cms.services.Term.TermQueryService;
import com.comp3000.project.cms.services.User.UserQueryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormCourseOfferingConverter extends Converter<CourseOffering, CourseOfferingForm> {

    @Autowired
    private CourseQueryService courseQueryService;
    @Autowired
    private TermQueryService termQueryService;
    @Autowired
    private UserQueryService userQueryService;

    @Override
    public CourseOffering convert(CourseOfferingForm courseOfferingForm) throws FieldNotValidException {

        CourseOffering courseOffering = new CourseOffering();

        try{
            courseOffering.setCourse(courseQueryService.getById(courseOfferingForm.getCourse()));
        }catch (NotFoundException e){
            throw new FieldNotValidException(courseOffering, "course", e.getMessage());
        }

        try{
            courseOffering.setTerm(termQueryService.getById(courseOfferingForm.getTerm()));
        }catch (NotFoundException e){
            throw new FieldNotValidException(courseOffering, "term", e.getMessage());
        }

        try{
            courseOffering.setProfessor(userQueryService.getById(courseOfferingForm.getProfessor()));
        }catch (NotFoundException e){
            throw new FieldNotValidException(courseOffering, "professor", e.getMessage());
        }

        courseOffering.setCapacity(courseOfferingForm.getCapacity());

        return courseOffering;
    }
}


