package com.comp3000.project.cms.BLL.DropCourse;

import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.Term;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.components.CMS;
import com.comp3000.project.cms.DAL.services.CourseOffering.CourseOfferingCommandService;
import org.springframework.data.util.Pair;

import java.sql.Date;

public class DropCourseOfferingWithWDNAndReimbursementHandler extends Handler<Pair<CourseOffering, User>> {
    private CourseOfferingCommandService courseOfferingCommandService;
    private CMS cms;

    public DropCourseOfferingWithWDNAndReimbursementHandler(CourseOfferingCommandService courseOfferingCommandService, CMS cms) {
        this.courseOfferingCommandService = courseOfferingCommandService;
        this.cms = cms;
    }

    @Override
    public Status<Pair<CourseOffering, User>> handle(Pair<CourseOffering, User> studentRegisteredCourseRelationship) {
        Date currentTime = cms.getCurrentTime();
        Term currentTerm = cms.getCurrentTerm();

        if (currentTime.before(currentTerm.getWdnDeadline()))
            return next.handle(studentRegisteredCourseRelationship);

        this.courseOfferingCommandService.dropCourseOfferingWithWDN(studentRegisteredCourseRelationship.getFirst(), studentRegisteredCourseRelationship.getSecond());
        return Status.ok(studentRegisteredCourseRelationship);
    }
}
