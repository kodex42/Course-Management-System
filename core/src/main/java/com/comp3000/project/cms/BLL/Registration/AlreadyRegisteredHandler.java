package com.comp3000.project.cms.BLL.Registration;

import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.BLL.UserManagementBL;
import com.comp3000.project.cms.DAO.RegApplication;
import com.comp3000.project.cms.DAL.services.User.UserQueryService;

public class AlreadyRegisteredHandler extends Handler<RegApplication> {
    private UserQueryService userQueryService;

    public AlreadyRegisteredHandler(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public Status handle(RegApplication ap) {
        if (!UserManagementBL.userExists(userQueryService.getAllUsers(), ap.getEmail()))
            return next != null ? next.handle(ap) : Status.ok(ap);
        return Status.failed(ap, "User with email "
                + ap.getEmail() +
                " already exists");
    }
}
