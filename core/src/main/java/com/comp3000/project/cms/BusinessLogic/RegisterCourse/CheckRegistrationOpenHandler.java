package com.comp3000.project.cms.BusinessLogic.RegisterCourse;

import com.comp3000.project.cms.BusinessLogic.CourseRegistrationBL;
import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.components.CMS;
import org.springframework.data.util.Pair;

public class CheckRegistrationOpenHandler extends Handler<Pair<CourseOffering, User>> {
    private CMS cms;

    public CheckRegistrationOpenHandler(CMS cms) {
        this.cms = cms;
    }

    @Override
    public Status<Pair<CourseOffering, User>> handle(Pair<CourseOffering, User> studentRegisteredCourseRelationship) {
        if (CourseRegistrationBL.registrationOpen(studentRegisteredCourseRelationship.getFirst(), cms.getCurrentTerm(), cms.getCurrentTime()))
            return next != null ? next.handle(studentRegisteredCourseRelationship) : Status.ok(studentRegisteredCourseRelationship);;
        return Status.failed(studentRegisteredCourseRelationship, "The registration period is closed");
    }
}
