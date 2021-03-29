package com.comp3000.project.cms.BusinessLogic.RegisterCourse;

import com.comp3000.project.cms.BusinessLogic.CourseRegistrationBL;
import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.User;
import org.springframework.data.util.Pair;

public class CheckSatisfiesPreclusionsHandler extends Handler<Pair<CourseOffering, User>> {

    @Override
    public Status<Pair<CourseOffering, User>> handle(Pair<CourseOffering, User> studentRegisteredCourseRelationship) {
        if (CourseRegistrationBL.satisfiesPreclusions(studentRegisteredCourseRelationship.getFirst().getCourse(), studentRegisteredCourseRelationship.getSecond()))
            return next != null ? next.handle(studentRegisteredCourseRelationship) : Status.ok(studentRegisteredCourseRelationship);
        return Status.failed(studentRegisteredCourseRelationship, "Student "
                + studentRegisteredCourseRelationship.getSecond().getName()
                + " has existing preclusions for "
                + studentRegisteredCourseRelationship.getFirst().toString());
    }
}
