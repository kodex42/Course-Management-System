package com.comp3000.project.cms.BusinessLogic.DropCourse;

import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.User;
import org.springframework.data.util.Pair;

public class CheckStudentRegisteredInCourseOffering extends Handler<Pair<CourseOffering, User>> {

    public CheckStudentRegisteredInCourseOffering() {
    }

    @Override
    public Status<Pair<CourseOffering, User>> handle(Pair<CourseOffering, User> studentRegisteredCourseRelationship) {
        CourseOffering courseOffering = studentRegisteredCourseRelationship.getFirst();
        User student = studentRegisteredCourseRelationship.getSecond();

        if (courseOffering.getStudents().contains(student))
            return next.handle(studentRegisteredCourseRelationship);
        return Status.failed(studentRegisteredCourseRelationship, "Students cannot drop courses they aren't registered for");
    }
}
