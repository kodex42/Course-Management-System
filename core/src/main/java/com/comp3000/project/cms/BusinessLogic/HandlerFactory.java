package com.comp3000.project.cms.BusinessLogic;

import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.DAC.User;
import org.springframework.data.util.Pair;

public interface HandlerFactory {
    Handler<RegApplication> createApplicationRegistrationHandler();
    Handler<Term> createTermCreationHandler();
    Handler<User> createUserDeletionHandler();
    Handler<Pair<CourseOffering, User>> createRegisterCourseOfferingHandler();
    Handler<Pair<CourseOffering, User>> createDropCourseOfferingHandler();
}
