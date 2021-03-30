package com.comp3000.project.cms.DAL.services.CourseOffering;

import com.comp3000.project.cms.BLL.CourseRegistrationBL;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.components.CMS;
import com.comp3000.project.cms.BLL.converters.FormCourseOfferingConverter;
import com.comp3000.project.cms.exception.CannotRegisterException;
import com.comp3000.project.cms.exception.FieldNotValidException;
import com.comp3000.project.cms.web.forms.CourseOfferingForm;
import com.comp3000.project.cms.DAL.repository.CourseOfferingRepository;
import com.comp3000.project.cms.DAL.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

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
    @Autowired
    private EmailService emailService;

    public CourseOffering createCourse(CourseOfferingForm courseOfferingForm) throws FieldNotValidException, EntityExistsException {
        CourseOffering courseOffering = formCourseOfferingConverter.covert(courseOfferingForm);

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

    public void dropCourseOffering(CourseOffering courseOffering, User student) {
        courseOffering.getStudents().remove(student);

        courseOfferingRepository.save(courseOffering);
    }

    public void dropCourseOfferingWithWDN(CourseOffering courseOffering, User student) {
        // TODO: Set grade of WDN and email student about refund in process instead
        dropCourseOffering(courseOffering, student);
    }

    public void dropCourseOfferingWithNoReimbursement(CourseOffering courseOffering, User student) {
        // TODO: Set grade of WDN and email student about no refund instead
        dropCourseOffering(courseOffering, student);
    }
}
