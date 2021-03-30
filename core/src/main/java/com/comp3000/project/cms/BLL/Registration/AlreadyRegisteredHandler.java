package com.comp3000.project.cms.BLL.Registration;

import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAO.RegApplication;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.DAL.services.User.UserQueryService;

public class AlreadyRegisteredHandler extends Handler<RegApplication> {
    private UserQueryService userQueryService;

    public AlreadyRegisteredHandler(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public Status handle(RegApplication ap) {
        User user = this.userQueryService.getByUsername(ap.getEmail());
        if (user == null) {
            if (next != null) {
                return this.next.handle(ap);
            }
        } else {
            return Status.failed(ap, "User with email " + ap.getEmail() + " already exists");
        }

        return Status.ok(ap);
    }
}
