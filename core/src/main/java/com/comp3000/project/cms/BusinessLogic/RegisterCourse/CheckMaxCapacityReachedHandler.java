package com.comp3000.project.cms.BusinessLogic.RegisterCourse;

import com.comp3000.project.cms.BusinessLogic.CourseRegistrationBL;
import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.User;
import org.springframework.data.util.Pair;

public class CheckMaxCapacityReachedHandler extends Handler<Pair<CourseOffering, User>> {

    @Override
    public Status<Pair<CourseOffering, User>> handle(Pair<CourseOffering, User> studentRegisteredCourseRelationship) {
        if (!CourseRegistrationBL.maxCapacityReached(studentRegisteredCourseRelationship.getFirst()))
            return next != null ? next.handle(studentRegisteredCourseRelationship) : Status.ok(studentRegisteredCourseRelationship);
        return Status.failed(studentRegisteredCourseRelationship, "Max capacity reached for course offering "
                + studentRegisteredCourseRelationship.getFirst().toString());
    }
}
