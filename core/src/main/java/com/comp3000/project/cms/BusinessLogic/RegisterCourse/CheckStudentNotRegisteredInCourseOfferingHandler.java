package com.comp3000.project.cms.BusinessLogic.RegisterCourse;

import com.comp3000.project.cms.BusinessLogic.CourseRegistrationBL;
import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.User;
import org.springframework.data.util.Pair;

public class CheckStudentNotRegisteredInCourseOfferingHandler extends Handler<Pair<CourseOffering, User>> {
    @Override
    public Status<Pair<CourseOffering, User>> handle(Pair<CourseOffering, User> studentRegisteredCourseRelationship) {
        if (!CourseRegistrationBL.isRegistered(studentRegisteredCourseRelationship.getFirst(), studentRegisteredCourseRelationship.getSecond()))
            return next != null ? next.handle(studentRegisteredCourseRelationship) : Status.ok(studentRegisteredCourseRelationship);
        return Status.failed(studentRegisteredCourseRelationship, "Students cannot register for courses they are already registered in");
    }
}
