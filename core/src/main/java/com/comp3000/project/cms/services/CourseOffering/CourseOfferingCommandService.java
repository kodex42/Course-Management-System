package com.comp3000.project.cms.services.CourseOffering;

import com.comp3000.project.cms.BusinessLogic.*;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.components.CMS;
import com.comp3000.project.cms.converters.FormCourseOfferingConverter;
import com.comp3000.project.cms.exception.CannotDropException;
import com.comp3000.project.cms.exception.CannotRegisterException;
import com.comp3000.project.cms.exception.FieldNotValidException;
import com.comp3000.project.cms.forms.CourseOfferingForm;
import com.comp3000.project.cms.repository.CourseOfferingRepository;
import com.comp3000.project.cms.services.EmailService;
import com.comp3000.project.cms.services.User.UserQueryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.sql.Date;

@Service
public class CourseOfferingCommandService {
    @Autowired
    private UserQueryService userQueryService;
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
    @Autowired
    private BusinessLogicHandlerFactory factory;

    private void secureRegisterInCourseOffering(CourseOffering courseOffering, User student) {
        courseOffering.getStudents().add(student);
        courseOfferingRepository.save(courseOffering);
    }

    private void secureDropCourseOffering(CourseOffering courseOffering, User student, boolean wdn, boolean reimbursement) {
        // TODO: change operation based on wdn and reimbursement
        courseOffering.getStudents().remove(student);
        courseOfferingRepository.save(courseOffering);
    }

    public CourseOffering createCourse(CourseOfferingForm courseOfferingForm) throws FieldNotValidException, EntityExistsException {
        CourseOffering courseOffering = formCourseOfferingConverter.convert(courseOfferingForm);

        if(courseOfferingQueryService.existsDuplicate(courseOffering))
            throw new EntityExistsException("Course offering with specified info already exists");

        return courseOfferingRepository.save(courseOffering);
    }

    public void registerInCourseOffering(Integer courseOffrId, String studentName) throws CannotRegisterException, NotFoundException {
        CourseOffering courseOffering = courseOfferingQueryService.getById(courseOffrId);
        User student = userQueryService.getByUsername(studentName);

        Handler<Pair<CourseOffering, User>> registerHandlerChain = factory.createRegisterCourseOfferingHandler();
        Status<Pair<CourseOffering, User>> status = registerHandlerChain.handle(Pair.of(courseOffering, student));

        if (status.isSuccessful())
            secureRegisterInCourseOffering(courseOffering, student);
        else
            throw new CannotRegisterException("Cannot register for course: " + status.getError());
    }

    public void dropCourseOffering(Integer courseOffrId, String studentName) throws CannotDropException, NotFoundException {
        CourseOffering courseOffering = courseOfferingQueryService.getById(courseOffrId);
        User student = userQueryService.getByUsername(studentName);

        Handler<Pair<CourseOffering, User>> dropHandlerChain = factory.createDropCourseOfferingHandler();
        Status<Pair<CourseOffering, User>> status = dropHandlerChain.handle(Pair.of(courseOffering, student));

        if (status.isSuccessful()) {
            Date currentTime = cms.getCurrentTime();
            Term currentTerm = cms.getCurrentTerm();

            if (currentTime.after(currentTerm.getReimbursementDeadline()))
                secureDropCourseOffering(courseOffering, student, true, false);
            else secureDropCourseOffering(courseOffering, student, currentTime.after(currentTerm.getWdnDeadline()), true);
        }
        else
            throw new CannotDropException("Cannot drop course: " + status.getError());
    }
}
