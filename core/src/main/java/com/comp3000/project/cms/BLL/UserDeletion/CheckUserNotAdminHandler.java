package com.comp3000.project.cms.BLL.UserDeletion;

import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.BLL.UserManagementBL;
import com.comp3000.project.cms.DAO.User;

public class CheckUserNotAdminHandler extends Handler<User> {

    @Override
    public Status<User> handle(User user) {
        if (UserManagementBL.userIsAdmin(user))
            return next != null ? next.handle(user) : Status.ok(user);
        return Status.failed(user, "Admin user cannot be deleted");
    }
}
