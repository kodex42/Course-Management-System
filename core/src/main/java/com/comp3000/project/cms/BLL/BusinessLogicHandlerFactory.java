package com.comp3000.project.cms.BLL;

import com.comp3000.project.cms.BLL.DeliverableSubmission.CheckBeforeDeliverableDeadlineHandler;
import com.comp3000.project.cms.BLL.DropCourse.CheckCourseOfferingWithinCurrentTermHandler;
import com.comp3000.project.cms.BLL.DropCourse.CheckStudentRegisteredInCourseOfferingHandler;
import com.comp3000.project.cms.BLL.DropCourse.CheckValidWithdrawalPeriodHandler;
import com.comp3000.project.cms.BLL.RegisterCourse.*;
import com.comp3000.project.cms.BLL.Registration.AlreadyRegisteredHandler;
import com.comp3000.project.cms.BLL.Registration.ApplicationInProcessHandler;
import com.comp3000.project.cms.BLL.TermCreation.CheckOverlappingTermHandler;
import com.comp3000.project.cms.BLL.UserDeletion.CheckUserNotAdminHandler;
import com.comp3000.project.cms.BLL.UserDeletion.CheckUserNotAssociatedWithAnyCoursesHandler;
import com.comp3000.project.cms.DAL.services.CourseOffering.CourseOfferingCommandService;
import com.comp3000.project.cms.DAL.services.RegApplication.RegApplicationCommandService;
import com.comp3000.project.cms.DAL.services.RegApplication.RegApplicationQueryService;
import com.comp3000.project.cms.DAL.services.Term.TermCommandService;
import com.comp3000.project.cms.DAL.services.Term.TermQueryService;
import com.comp3000.project.cms.DAL.services.User.UserCommandService;
import com.comp3000.project.cms.DAL.services.User.UserQueryService;
import com.comp3000.project.cms.DAO.*;
import com.comp3000.project.cms.components.CMS;
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
        Handler<RegApplication> handler1 = new AlreadyRegisteredHandler(userQueryService);
        Handler<RegApplication> handler2 = new ApplicationInProcessHandler(regApplicationQueryService);

        handler2.setNext(handler1);

        return handler2;
    }

    @Override
    public Handler<Term> createTermCreationHandler() {
        return new CheckOverlappingTermHandler(termQueryService);
    }

    @Override
    public Handler<Submission> createSubmissionHandler() {
        Handler<Submission> handler1 = new CheckBeforeDeliverableDeadlineHandler(cms);

        return handler1;
    }

    @Override
    public Handler<User> createUserDeletionHandler() {
        Handler<User> handler1 = new CheckUserNotAssociatedWithAnyCoursesHandler();
        Handler<User> handler2 = new CheckUserNotAdminHandler();

        handler2.setNext(handler1);

        return handler2;
    }

    @Override
    public Handler<Pair<CourseOffering, User>> createRegisterCourseOfferingHandler() {
        Handler<Pair<CourseOffering, User>> handler1 = new CheckSatisfiesPreclusionsHandler();
        Handler<Pair<CourseOffering, User>> handler2 = new CheckSatisfiesPrerequisitesHandler(cms);
        Handler<Pair<CourseOffering, User>> handler3 = new CheckMaxCapacityReachedHandler();
        Handler<Pair<CourseOffering, User>> handler4 = new CheckRegistrationOpenHandler(cms);
        Handler<Pair<CourseOffering, User>> handler5 = new CheckStudentNotRegisteredInCourseOfferingHandler();

        handler5.setNext(handler4);
        handler4.setNext(handler3);
        handler3.setNext(handler2);
        handler2.setNext(handler1);

        return handler5;
    }

    @Override
    public Handler<Pair<CourseOffering, User>> createDropCourseOfferingHandler() {
        Handler<Pair<CourseOffering, User>> handler1 = new CheckValidWithdrawalPeriodHandler(cms);
        Handler<Pair<CourseOffering, User>> handler2 = new CheckCourseOfferingWithinCurrentTermHandler(cms);
        Handler<Pair<CourseOffering, User>> handler3 = new CheckStudentRegisteredInCourseOfferingHandler();

        handler3.setNext(handler2);
        handler2.setNext(handler1);

        return handler3;
    }
}
