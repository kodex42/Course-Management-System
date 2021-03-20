package com.comp3000.project.cms.BusinessLogic.Registration;

import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.services.RegApplication.RegApplicationCommandService;

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
