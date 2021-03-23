package com.comp3000.project.cms.BusinessLogic;

import com.comp3000.project.cms.BusinessLogic.Registration.RegisterApplicationHandler;
import com.comp3000.project.cms.BusinessLogic.Registration.AlreadyRegisteredHandler;
import com.comp3000.project.cms.BusinessLogic.Registration.ApplicationInProcessHandler;
import com.comp3000.project.cms.BusinessLogic.TermCreation.CheckOverlappingTermHandler;
import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.services.RegApplication.RegApplicationCommandService;
import com.comp3000.project.cms.services.RegApplication.RegApplicationQueryService;
import com.comp3000.project.cms.services.Term.TermCommandService;
import com.comp3000.project.cms.services.Term.TermQueryService;
import com.comp3000.project.cms.services.User.UserCommandService;
import com.comp3000.project.cms.services.User.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessLogicHandlerFactory implements HandlerFactory {
    @Autowired
    private RegApplicationCommandService regApplicationCommandService;
    @Autowired
    private RegApplicationQueryService regApplicationQueryService;
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private UserCommandService userCommandService;
    @Autowired
    private TermQueryService termQueryService;
    @Autowired
    private TermCommandService termCommandService;

    @Override
    public Handler<RegApplication> createApplicationRegistrationHandler() {
        Handler<RegApplication> handler1 = new RegisterApplicationHandler(regApplicationCommandService);
        Handler<RegApplication> handler2 = new AlreadyRegisteredHandler(userQueryService);
        Handler<RegApplication> handler3 = new ApplicationInProcessHandler(regApplicationQueryService);

        handler3.setNext(handler2);
        handler2.setNext(handler1);

        return handler3;
    }

    @Override
    public Handler<Term> createTermCreationHandler() {
        return new CheckOverlappingTermHandler(termQueryService, termCommandService);
    }
}
