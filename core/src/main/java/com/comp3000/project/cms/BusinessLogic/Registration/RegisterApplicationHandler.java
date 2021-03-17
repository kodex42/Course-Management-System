package com.comp3000.project.cms.BusinessLogic.Registration;

import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.repository.RegApplicationRepository;
import org.springframework.stereotype.Service;

public class RegisterApplicationHandler extends RegistrationHandler {
    private RegApplicationRepository regApplicationRepository;

    public RegisterApplicationHandler(RegApplicationRepository regApplicationRepository) {
        this.regApplicationRepository = regApplicationRepository;
    }

    @Override
    public RegistrationStatus handle(RegApplication ap) {
         try {
             RegApplication saveAp = this.regApplicationRepository.save(ap);
             if (next != null) {
                 return this.next.handle(ap);
             }
         } catch (Exception e) {
             return RegistrationStatus.failed(ap, "Application with "
                     + ap.getEmail() +
                     " was not created. Please try again later");
         }

        return RegistrationStatus.ok(ap);
    }
}
