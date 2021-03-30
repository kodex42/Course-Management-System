package com.comp3000.project.cms.BLL.DropCourse;

import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.DAL.services.CourseOffering.CourseOfferingCommandService;
import org.springframework.data.util.Pair;

public class DropCourseOfferingFullReimbursementHandler extends Handler<Pair<CourseOffering, User>> {
    private CourseOfferingCommandService courseOfferingCommandService;

    public DropCourseOfferingFullReimbursementHandler(CourseOfferingCommandService courseOfferingCommandService) {
        this.courseOfferingCommandService = courseOfferingCommandService;
    }

    @Override
    public Status<Pair<CourseOffering, User>> handle(Pair<CourseOffering, User> studentRegisteredCourseRelationship) {
        this.courseOfferingCommandService.dropCourseOffering(studentRegisteredCourseRelationship.getFirst(), studentRegisteredCourseRelationship.getSecond());

        return Status.ok(studentRegisteredCourseRelationship);
    }
}
