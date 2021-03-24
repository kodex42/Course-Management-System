package com.comp3000.project.cms.BusinessLogic.DropCourse;

import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.components.CMS;
import com.comp3000.project.cms.services.CourseOffering.CourseOfferingCommandService;
import org.springframework.data.util.Pair;

import java.sql.Date;

public class DropCourseOfferingWithWDNAndNoReimbursementHandler extends Handler<Pair<CourseOffering, User>> {
    private CourseOfferingCommandService courseOfferingCommandService;
    private CMS cms;

    public DropCourseOfferingWithWDNAndNoReimbursementHandler(CourseOfferingCommandService courseOfferingCommandService, CMS cms) {
        this.courseOfferingCommandService = courseOfferingCommandService;
        this.cms = cms;
    }

    @Override
    public Status<Pair<CourseOffering, User>> handle(Pair<CourseOffering, User> studentRegisteredCourseRelationship) {
        Date currentTime = cms.getCurrentTime();
        Term currentTerm = cms.getCurrentTerm();

        if (currentTime.before(currentTerm.getReimbursementDeadline()))
            return next.handle(studentRegisteredCourseRelationship);

        this.courseOfferingCommandService.dropCourseOfferingWithNoReimbursement(studentRegisteredCourseRelationship.getFirst(), studentRegisteredCourseRelationship.getSecond());
        return Status.ok(studentRegisteredCourseRelationship);
    }
}
