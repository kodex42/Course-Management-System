package com.comp3000.project.cms.BLL.Registration;

import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAO.RegApplication;
import com.comp3000.project.cms.DAL.services.RegApplication.RegApplicationCommandService;

public class RegisterApplicationHandler extends Handler<RegApplication> {
    private RegApplicationCommandService regApplicationCommandService;

    public RegisterApplicationHandler(RegApplicationCommandService regApplicationQueryService) {
        this.regApplicationCommandService = regApplicationQueryService;
    }

    @Override
    public Status<RegApplication> handle(RegApplication ap) {
        try {
            RegApplication saved = this.regApplicationCommandService.create(ap);
            if (next != null) {
                return this.next.handle(ap);
            }
        } catch (Exception e) {
            return Status.failed(ap, "Application with "
                    + ap.getEmail() +
                    " was not created. Please try again later");
        }

        return Status.ok(ap);
    }
}
