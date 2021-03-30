package com.comp3000.project.cms.BLL;

import com.comp3000.project.cms.BLL.DropCourse.*;
import com.comp3000.project.cms.BLL.Registration.AlreadyRegisteredHandler;
import com.comp3000.project.cms.BLL.Registration.ApplicationInProcessHandler;
import com.comp3000.project.cms.BLL.Registration.RegisterApplicationHandler;
import com.comp3000.project.cms.BLL.TermCreation.CheckOverlappingTermHandler;
import com.comp3000.project.cms.BLL.UserDeletion.CheckUserNotAdminHandler;
import com.comp3000.project.cms.BLL.UserDeletion.CheckUserNotAssociatedWithAnyCourses;
import com.comp3000.project.cms.BLL.UserDeletion.UserDeletionHandler;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.RegApplication;
import com.comp3000.project.cms.DAO.Term;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.components.CMS;
import com.comp3000.project.cms.DAL.services.CourseOffering.CourseOfferingCommandService;
import com.comp3000.project.cms.DAL.services.RegApplication.RegApplicationCommandService;
import com.comp3000.project.cms.DAL.services.RegApplication.RegApplicationQueryService;
import com.comp3000.project.cms.DAL.services.Term.TermCommandService;
import com.comp3000.project.cms.DAL.services.Term.TermQueryService;
import com.comp3000.project.cms.DAL.services.User.UserCommandService;
import com.comp3000.project.cms.DAL.services.User.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class BusinessLogicHandlerFactory implements HandlerFactory {
    @Autowired
    private CMS cms;
    @Autowired
    private RegApplicationCommandService regApplicationCommandService;
    @Autowired
    private RegApplicationQueryService regApplicationQueryService;
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private UserCommandService userCommandService;
    @Autowired
    private TermQueryService termQueryService;
    @Autowired
    private TermCommandService termCommandService;
    @Autowired
    private CourseOfferingCommandService courseOfferingCommandService;

    @Override
    public Handler<RegApplication> createApplicationRegistrationHandler() {
        Handler<RegApplication> handler1 = new RegisterApplicationHandler(regApplicationCommandService);
        Handler<RegApplication> handler2 = new AlreadyRegisteredHandler(userQueryService);
        Handler<RegApplication> handler3 = new ApplicationInProcessHandler(regApplicationQueryService);

        handler3.setNext(handler2);
        handler2.setNext(handler1);

        return handler3;
    }

    @Override
    public Handler<Term> createTermCreationHandler() {
        return new CheckOverlappingTermHandler(termQueryService, termCommandService);
    }

    @Override
    public Handler<User> createUserDeletionHandler() {
        Handler<User> handler1 = new UserDeletionHandler(userCommandService, userQueryService);
        Handler<User> handler2 = new CheckUserNotAssociatedWithAnyCourses();
        Handler<User> handler3 = new CheckUserNotAdminHandler();

        handler3.setNext(handler2);
        handler2.setNext(handler1);

        return handler3;
    }

    @Override
    public Handler<Pair<CourseOffering, User>> createDropCourseOfferingHandler() {
        Handler<Pair<CourseOffering, User>> handler1 = new DropCourseOfferingFullReimbursementHandler(courseOfferingCommandService);
        Handler<Pair<CourseOffering, User>> handler2 = new DropCourseOfferingWithWDNAndReimbursementHandler(courseOfferingCommandService, cms);
        Handler<Pair<CourseOffering, User>> handler3 = new DropCourseOfferingWithWDNAndNoReimbursementHandler(courseOfferingCommandService, cms);
        Handler<Pair<CourseOffering, User>> handler4 = new CheckValidWithdrawalPeriod(cms);
        Handler<Pair<CourseOffering, User>> handler5 = new CheckCourseOfferingWithinCurrentTerm(cms);
        Handler<Pair<CourseOffering, User>> handler6 = new CheckStudentRegisteredInCourseOffering();

        handler6.setNext(handler5);
        handler5.setNext(handler4);
        handler4.setNext(handler3);
        handler3.setNext(handler2);
        handler2.setNext(handler1);

        return handler6;
    }
}
