package com.comp3000.project.cms.BusinessLogic.Registration;

import com.comp3000.project.cms.repository.RegApplicationRepository;
import com.comp3000.project.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class NewUsersRegistrationHandlerFactory extends RegistrationHandlerFactory {
    public NewUsersRegistrationHandlerFactory(UserRepository userRep, RegApplicationRepository regAppRep) {
        super(userRep, regAppRep);
    }

    public RegistrationHandler createRegistrationHandler() {
        RegistrationHandler handler1 = new RegisterApplicationHandler(regApplicationRepository);
        RegistrationHandler handler2 = new AlreadyRegisteredHandler(userRepository);
        RegistrationHandler handler3 = new ApplicationInProcessHandler(regApplicationRepository);

        handler3.setNext(handler2);
        handler2.setNext(handler1);

        return handler3;
    }
}
