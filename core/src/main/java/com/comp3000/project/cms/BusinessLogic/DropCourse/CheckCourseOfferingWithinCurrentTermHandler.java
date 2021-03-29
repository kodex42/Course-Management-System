package com.comp3000.project.cms.BusinessLogic.DropCourse;

import com.comp3000.project.cms.BusinessLogic.CourseDroppingBL;
import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.components.CMS;
import org.springframework.data.util.Pair;

public class CheckCourseOfferingWithinCurrentTermHandler extends Handler<Pair<CourseOffering, User>> {
    private CMS cms;

    public CheckCourseOfferingWithinCurrentTermHandler(CMS cms) {
        this.cms = cms;
    }

    @Override
    public Status<Pair<CourseOffering, User>> handle(Pair<CourseOffering, User> studentRegisteredCourseRelationship) {
        if (CourseDroppingBL.courseOfferingWithinCurrentTerm(studentRegisteredCourseRelationship.getFirst(), cms.getCurrentTerm()))
            return next != null ? next.handle(studentRegisteredCourseRelationship) : Status.ok(studentRegisteredCourseRelationship);
        return Status.failed(studentRegisteredCourseRelationship, "Courses outside the current term may not be dropped");
    }
}
