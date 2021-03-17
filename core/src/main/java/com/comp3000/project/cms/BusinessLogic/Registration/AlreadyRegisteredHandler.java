package com.comp3000.project.cms.BusinessLogic.Registration;

import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.repository.UserRepository;

public class AlreadyRegisteredHandler extends RegistrationHandler {
    private UserRepository userRepository;

    public AlreadyRegisteredHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RegistrationStatus handle(RegApplication ap) {
        User user = this.userRepository.findByUsername(ap.getEmail());
        if (user == null) {
            if (next != null) {
                return this.next.handle(ap);
            }
        } else {
            return RegistrationStatus.failed(ap, "User with email " + ap.getEmail() + " already exists");
        }

        return RegistrationStatus.ok(ap);
    }
}
