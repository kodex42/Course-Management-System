package com.comp3000.project.cms.BusinessLogic.Registration;

import com.comp3000.project.cms.repository.RegApplicationRepository;
import com.comp3000.project.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class RegistrationHandlerFactory {
    protected final UserRepository userRepository;
    protected final RegApplicationRepository regApplicationRepository;

    public RegistrationHandlerFactory(UserRepository userRep, RegApplicationRepository regAppRep) {
        userRepository = userRep;
        regApplicationRepository = regAppRep;
    }

    abstract public RegistrationHandler createRegistrationHandler();
}
