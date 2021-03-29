package com.comp3000.project.cms.services.RegApplication;

import com.comp3000.project.cms.BusinessLogic.BusinessLogicHandlerFactory;
import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.exception.CannotCreateException;
import com.comp3000.project.cms.repository.RegApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegApplicationCommandService {
    @Autowired
    private RegApplicationRepository regApplicationRepository;
    @Autowired
    private BusinessLogicHandlerFactory factory;

    private void secureCreate(RegApplication app) {
        this.regApplicationRepository.save(app);
    }

    public void create(RegApplication app) throws CannotCreateException {
        Handler<RegApplication> applicationHandlerCahin = factory.createApplicationRegistrationHandler();
        Status<RegApplication> status = applicationHandlerCahin.handle(app);

        if (status.isSuccessful())
            secureCreate(app);
        else
            throw new CannotCreateException("Could not create registration application: " + status.getError());
    }

    public void deleteById(Integer id) {
        this.regApplicationRepository.deleteById(id);
    }

    public void delete(RegApplication app) {
        this.deleteById(app.getId());
    }
}
