package com.comp3000.project.cms.BusinessLogic.Registration;

import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.BusinessLogic.UserManagementBL;
import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.services.User.UserQueryService;

public class AlreadyRegisteredHandler extends Handler<RegApplication> {
    private UserQueryService userQueryService;

    public AlreadyRegisteredHandler(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public Status handle(RegApplication ap) {
        if (UserManagementBL.userExists(userQueryService, ap.getEmail()))
            return next != null ? next.handle(ap) : Status.ok(ap);
        return Status.failed(ap, "User with email "
                + ap.getEmail() +
                " already exists");
    }
}
