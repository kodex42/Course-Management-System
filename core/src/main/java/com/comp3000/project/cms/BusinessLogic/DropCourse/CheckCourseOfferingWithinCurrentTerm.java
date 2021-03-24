package com.comp3000.project.cms.BusinessLogic.DropCourse;

import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.Course;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.components.CMS;
import org.springframework.data.util.Pair;

public class CheckCourseOfferingWithinCurrentTerm extends Handler<Pair<CourseOffering, User>> {
    private CMS cms;

    public CheckCourseOfferingWithinCurrentTerm(CMS cms) {
        this.cms = cms;
    }

    @Override
    public Status<Pair<CourseOffering, User>> handle(Pair<CourseOffering, User> studentRegisteredCourseRelationship) {
        CourseOffering courseOffering = studentRegisteredCourseRelationship.getFirst();
        if (courseOffering.getTerm().compareTo(cms.getCurrentTerm()) == 0)
            return next.handle(studentRegisteredCourseRelationship);
        return Status.failed(studentRegisteredCourseRelationship, "Courses outside the current term may not be dropped");
    }
}
