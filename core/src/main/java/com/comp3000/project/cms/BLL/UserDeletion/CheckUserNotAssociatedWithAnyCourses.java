package com.comp3000.project.cms.BLL.UserDeletion;

import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAO.User;

public class CheckUserNotAssociatedWithAnyCourses extends Handler<User> {

    public CheckUserNotAssociatedWithAnyCourses() {
    }

    @Override
    public Status<User> handle(User user) {
        if (user.getTeachingCourseOfferings().isEmpty() && user.getTakingCourseOfferings().isEmpty())
            return next != null ? next.handle(user) : Status.ok(user);
        return user.getAuthority().equals("STUDENT") ? Status.failed(user, "Students currently enrolled in courses cannot be deleted") : Status.failed(user, "Professors currently assigned to courses cannot be deleted");
    }
}
