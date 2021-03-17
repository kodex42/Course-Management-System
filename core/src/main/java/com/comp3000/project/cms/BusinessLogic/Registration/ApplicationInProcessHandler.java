package com.comp3000.project.cms.BusinessLogic.Registration;

import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.repository.RegApplicationRepository;
import com.comp3000.project.cms.repository.UserRepository;
import org.springframework.stereotype.Service;

public class ApplicationInProcessHandler extends RegistrationHandler {
    private RegApplicationRepository regApplicationRepository;

    public ApplicationInProcessHandler(RegApplicationRepository regApplicationRepository) {
        this.regApplicationRepository = regApplicationRepository;
    }

    @Override
    public RegistrationStatus handle(RegApplication ap) {
        if (this.regApplicationRepository.findByEmail(ap.getEmail()) == null) {
            if (next != null) {
                return this.next.handle(ap);
            }
        } else {
            return RegistrationStatus.failed(ap, "Application with "
                    + ap.getEmail() +
                    " email will be processed soon");
        }

        return RegistrationStatus.ok(ap);
    }
}
