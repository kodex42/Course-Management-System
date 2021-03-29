package com.comp3000.project.cms.BusinessLogic.UserDeletion;

import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.BusinessLogic.UserManagementBL;
import com.comp3000.project.cms.DAC.User;

public class CheckUserNotAssociatedWithAnyCoursesHandler extends Handler<User> {

    @Override
    public Status<User> handle(User user) {
        if (UserManagementBL.userNotTakingCourses(user) && UserManagementBL.userNotTeachingCourses(user))
            return next != null ? next.handle(user) : Status.ok(user);
        return user.getAuthority().equals("STUDENT") ? Status.failed(user, "Students currently enrolled in courses cannot be deleted") : Status.failed(user, "Professors currently assigned to courses cannot be deleted");
    }
}
