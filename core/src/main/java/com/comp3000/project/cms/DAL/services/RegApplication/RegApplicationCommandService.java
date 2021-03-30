package com.comp3000.project.cms.DAL.services.RegApplication;

import com.comp3000.project.cms.BLL.BusinessLogicHandlerFactory;
import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAO.RegApplication;
import com.comp3000.project.cms.DAL.repository.RegApplicationRepository;
import com.comp3000.project.cms.exception.CannotCreateException;
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
