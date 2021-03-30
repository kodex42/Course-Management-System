package com.comp3000.project.cms.BLL;

import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.RegApplication;
import com.comp3000.project.cms.DAO.Term;
import com.comp3000.project.cms.DAO.User;
import org.springframework.data.util.Pair;

public interface HandlerFactory {
    Handler<RegApplication> createApplicationRegistrationHandler();
    Handler<Term> createTermCreationHandler();
    Handler<User> createUserDeletionHandler();
    Handler<Pair<CourseOffering, User>> createRegisterCourseOfferingHandler();
    Handler<Pair<CourseOffering, User>> createDropCourseOfferingHandler();
}
