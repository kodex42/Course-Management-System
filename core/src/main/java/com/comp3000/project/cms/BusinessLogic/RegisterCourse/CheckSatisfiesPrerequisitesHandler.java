package com.comp3000.project.cms.BusinessLogic.RegisterCourse;

import com.comp3000.project.cms.BusinessLogic.CourseRegistrationBL;
import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.components.CMS;
import org.springframework.data.util.Pair;

public class CheckSatisfiesPrerequisitesHandler extends Handler<Pair<CourseOffering, User>> {
    private CMS cms;

    public CheckSatisfiesPrerequisitesHandler(CMS cms) {
        this.cms = cms;
    }

    @Override
    public Status<Pair<CourseOffering, User>> handle(Pair<CourseOffering, User> studentRegisteredCourseRelationship) {
        if (CourseRegistrationBL.satisfiesPrerequisites(studentRegisteredCourseRelationship.getFirst().getCourse(), studentRegisteredCourseRelationship.getSecond(), cms.getCurrentTerm()))
            return next != null ? next.handle(studentRegisteredCourseRelationship) : Status.ok(studentRegisteredCourseRelationship);
        return Status.failed(studentRegisteredCourseRelationship, "Student "
                + studentRegisteredCourseRelationship.getSecond().getName()
                + " does not meet required prerequisites for "
                + studentRegisteredCourseRelationship.getFirst().toString());
    }
}
