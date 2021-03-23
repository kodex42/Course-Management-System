package com.comp3000.project.cms.BusinessLogic.UserDeletion;

import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.User;

public class CheckUserNotAdminHandler extends Handler<User> {

    public CheckUserNotAdminHandler() {
    }

    @Override
    public Status<User> handle(User user) {
        if (user.getId() != 1)
            return next != null ? next.handle(user) : Status.ok(user);
        return Status.failed(user, "Admin user cannot be deleted");
    }
}
