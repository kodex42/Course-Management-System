package com.comp3000.project.cms.BusinessLogic.Registration;

import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.BusinessLogic.UserManagementBL;
import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.services.RegApplication.RegApplicationQueryService;

public class ApplicationInProcessHandler extends Handler<RegApplication> {
    private RegApplicationQueryService regApplicationQueryService;

    public ApplicationInProcessHandler(RegApplicationQueryService regApplicationQueryService) {
        this.regApplicationQueryService = regApplicationQueryService;
    }

    @Override
    public Status<RegApplication> handle(RegApplication ap) {
        if (UserManagementBL.userApplicationInProgress(regApplicationQueryService, ap.getEmail()))
            return next != null ? next.handle(ap) : Status.ok(ap);
        return Status.failed(ap, "Application with "
                + ap.getEmail() +
                " email will be processed soon");
    }
}
