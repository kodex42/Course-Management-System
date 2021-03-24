package com.comp3000.project.cms.services.CourseOffering;

import com.comp3000.project.cms.BusinessLogic.CourseRegistrationBL;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.components.CMS;
import com.comp3000.project.cms.converters.FormCourseOfferingConverter;
import com.comp3000.project.cms.exception.CannotRegisterException;
import com.comp3000.project.cms.exception.FieldNotValidException;
import com.comp3000.project.cms.forms.CourseOfferingForm;
import com.comp3000.project.cms.repository.CourseOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.stream.StreamSupport;

@Service
public class CourseOfferingCommandService {
    @Autowired
    private CourseOfferingQueryService courseOfferingQueryService;
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Autowired
    private FormCourseOfferingConverter formCourseOfferingConverter;
    @Autowired
    private CMS cms;

    public CourseOffering createCourse(CourseOfferingForm courseOfferingForm) throws FieldNotValidException, EntityExistsException {
        CourseOffering courseOffering = formCourseOfferingConverter.convert(courseOfferingForm);

        if(courseOfferingQueryService.existsDuplicate(courseOffering))
            throw new EntityExistsException("Course offering with specified info already exists");

        return courseOfferingRepository.save(courseOffering);
    }

    public void registerInCourseOffering(CourseOffering courseOffering, User student) throws CannotRegisterException{

        if(CourseRegistrationBL.isRegistered(courseOffering, student))
            throw new CannotRegisterException("Cannot register: already registered");

        if(!CourseRegistrationBL.registrationOpen(courseOffering, cms.getCurrentTerm(), cms.getCurrentTime()))
            throw new CannotRegisterException("Cannot register: registration is closed");

        if(CourseRegistrationBL.maxCapacityReached(courseOffering))
            throw new CannotRegisterException("Cannot register: max capacity reached");

        if(!CourseRegistrationBL.satisfiesPrerequisites(courseOffering.getCourse(), student, cms.getCurrentTerm()))
            throw new CannotRegisterException("Cannot register: prerequisites requirement not satisfied");

        if(!CourseRegistrationBL.satisfiesPreclusions(courseOffering.getCourse(), student))
            throw new CannotRegisterException("Cannot register: preclusions requirement not satisfied");

        courseOffering.getStudents().add(student);

        courseOfferingRepository.save(courseOffering);
    }

}
