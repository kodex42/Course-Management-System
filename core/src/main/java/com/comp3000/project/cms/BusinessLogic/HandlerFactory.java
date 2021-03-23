package com.comp3000.project.cms.BusinessLogic;

import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.repository.RegApplicationRepository;
import com.comp3000.project.cms.repository.UserRepository;
import com.comp3000.project.cms.services.UserCommandService;
import com.comp3000.project.cms.services.UserQueryService;

public interface HandlerFactory {
    Handler<RegApplication> createApplicationRegistrationHandler();
    Handler<Term> createTermCreationHandler();
}
