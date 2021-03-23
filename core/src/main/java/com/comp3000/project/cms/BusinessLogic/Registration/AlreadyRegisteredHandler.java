package com.comp3000.project.cms.BusinessLogic.Registration;

import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.repository.UserRepository;
import com.comp3000.project.cms.services.UserQueryService;

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
