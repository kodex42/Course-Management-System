package com.comp3000.project.cms.BLL.RegisterCourse;

import com.comp3000.project.cms.BLL.CourseRegistrationBL;
import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.User;
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
