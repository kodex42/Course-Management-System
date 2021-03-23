package com.comp3000.project.cms.BusinessLogic;

import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.DAC.User;

public interface HandlerFactory {
    Handler<RegApplication> createApplicationRegistrationHandler();
    Handler<Term> createTermCreationHandler();
    Handler<User> createUserDeletionHandler();
}
