package com.comp3000.project.cms.BLL.Registration;

import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.BLL.UserManagementBL;
import com.comp3000.project.cms.DAO.RegApplication;
import com.comp3000.project.cms.DAL.services.RegApplication.RegApplicationQueryService;

public class ApplicationInProcessHandler extends Handler<RegApplication> {
    private RegApplicationQueryService regApplicationQueryService;

    public ApplicationInProcessHandler(RegApplicationQueryService regApplicationQueryService) {
        this.regApplicationQueryService = regApplicationQueryService;
    }

    @Override
    public Status<RegApplication> handle(RegApplication ap) {
        if (!UserManagementBL.userApplicationInProgress(regApplicationQueryService.getAll(), ap.getEmail()))
            return next != null ? next.handle(ap) : Status.ok(ap);
        return Status.failed(ap, "Application with "
                + ap.getEmail() +
                " email will be processed soon");
    }
}
