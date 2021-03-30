package com.comp3000.project.cms.BLL.UserDeletion;

import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.BLL.UserManagementBL;
import com.comp3000.project.cms.DAO.User;

public class CheckUserNotAssociatedWithAnyCoursesHandler extends Handler<User> {

    @Override
    public Status<User> handle(User user) {
        if (UserManagementBL.userNotTakingCourses(user) && UserManagementBL.userNotTeachingCourses(user))
            return next != null ? next.handle(user) : Status.ok(user);
        return user.getAuthority().equals("STUDENT") ? Status.failed(user, "Students currently enrolled in courses cannot be deleted") : Status.failed(user, "Professors currently assigned to courses cannot be deleted");
    }
}
