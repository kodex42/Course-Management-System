package com.comp3000.project.cms.BLL;

import com.comp3000.project.cms.DAO.*;
import org.springframework.data.util.Pair;

public interface HandlerFactory {
    Handler<RegApplication> createApplicationRegistrationHandler();
    Handler<Term> createTermCreationHandler();
    Handler<Submission> createSubmissionHandler();
    Handler<User> createUserDeletionHandler();
    Handler<Pair<CourseOffering, User>> createRegisterCourseOfferingHandler();
    Handler<Pair<CourseOffering, User>> createDropCourseOfferingHandler();
    Handler<CourseOffering> createCourseOffrGradesSubmissionHandler();
}
