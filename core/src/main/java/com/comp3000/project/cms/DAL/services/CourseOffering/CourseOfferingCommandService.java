package com.comp3000.project.cms.DAL.services.CourseOffering;

import com.comp3000.project.cms.BLL.BusinessLogicHandlerFactory;
import com.comp3000.project.cms.BLL.CourseDroppingBL;
import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAL.services.User.UserQueryService;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.components.CMS;
import com.comp3000.project.cms.BLL.converters.FormCourseOfferingConverter;
import com.comp3000.project.cms.exception.CannotDeleteException;
import com.comp3000.project.cms.exception.CannotRegisterException;
import com.comp3000.project.cms.exception.FieldNotValidException;
import com.comp3000.project.cms.web.forms.CourseOfferingForm;
import com.comp3000.project.cms.DAL.repository.CourseOfferingRepository;
import com.comp3000.project.cms.DAL.services.EmailService;
import com.comp3000.project.cms.exception.CannotDropException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

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

    private void secureDelete(CourseOffering courseOffering) {
        courseOfferingRepository.delete(courseOffering);
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

        if (status.isSuccessful())
            switch (CourseDroppingBL.computeReimbursementType(cms.getCurrentTime(), cms.getCurrentTerm())) {
                case FULL_REIMBURSEMENT:
                    secureDropCourseOffering(courseOffering, student, false, true);
                    break;
                case REIMBURSEMENT_WITH_WDN:
                    secureDropCourseOffering(courseOffering, student, true, true);
                    break;
                case WDN_NO_REIMBURSEMENT:
                    secureDropCourseOffering(courseOffering, student, true, false);
                    break;
            }
        else
            throw new CannotDropException("Cannot drop course: " + status.getError());
    }

    public void delete(Integer courseOffrId) throws NotFoundException, CannotDeleteException {
        CourseOffering courseOffering = courseOfferingRepository.findById(courseOffrId).orElseThrow(() -> new NotFoundException("Course offering with id " + courseOffrId + " does not exist"));
        secureDelete(courseOffering);
        if (courseOfferingRepository.existsById(courseOffrId))
            throw new CannotDeleteException("Unable to delete course offering with id " + courseOffrId);
    }
}
