package com.comp3000.project.cms.BusinessLogic.Registration;

import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.repository.RegApplicationRepository;
import com.comp3000.project.cms.services.RegApplication.RegApplicationQueryService;

public class ApplicationInProcessHandler extends Handler<RegApplication> {
    private RegApplicationQueryService regApplicationQueryService;

    public ApplicationInProcessHandler(RegApplicationQueryService regApplicationQueryService) {
        this.regApplicationQueryService = regApplicationQueryService;
    }

    @Override
    public Status<RegApplication> handle(RegApplication ap) {
        if (this.regApplicationQueryService.getByEmail(ap.getEmail()) == null) {
            if (next != null) {
                return this.next.handle(ap);
            }
        } else {
            return Status.failed(ap, "Application with "
                    + ap.getEmail() +
                    " email will be processed soon");
        }

        return Status.ok(ap);
    }
}
