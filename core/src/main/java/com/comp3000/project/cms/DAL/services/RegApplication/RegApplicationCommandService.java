package com.comp3000.project.cms.DAL.services.RegApplication;

import com.comp3000.project.cms.BLL.BusinessLogicHandlerFactory;
import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAL.services.CommandService;
import com.comp3000.project.cms.DAO.Event;
import com.comp3000.project.cms.DAO.RegApplication;
import com.comp3000.project.cms.DAL.repository.RegApplicationRepository;
import com.comp3000.project.cms.common.EventType;
import com.comp3000.project.cms.components.CMS;
import com.comp3000.project.cms.exception.CannotCreateException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegApplicationCommandService extends CommandService {
    private RegApplicationQueryService regApplicationQueryService;
    @Autowired
    private RegApplicationRepository regApplicationRepository;
    @Autowired
    private BusinessLogicHandlerFactory factory;
    @Autowired
    private CMS cms;

    private void secureCreate(RegApplication app) {
        this.regApplicationRepository.save(app);

        notifyObservers(new Event(EventType.CREATION, cms.getCurrentTime(), "Registration application: " + app.toString()));
    }

    public void create(RegApplication app) throws CannotCreateException {
        Handler<RegApplication> applicationHandlerCahin = factory.createApplicationRegistrationHandler();
        Status<RegApplication> status = applicationHandlerCahin.handle(app);

        if (status.isSuccessful())
            secureCreate(app);
        else
            throw new CannotCreateException("Could not create registration application: " + status.getError());
    }

    public void deleteById(Integer id) throws NotFoundException {
        RegApplication regApplication = regApplicationQueryService.getById(id);

        regApplicationRepository.delete(regApplication);

        notifyObservers(new Event(EventType.DELETION, cms.getCurrentTime(), "Registration application: " + regApplication.toString()));
    }

    public void delete(RegApplication app) throws NotFoundException  {
        this.deleteById(app.getId());
    }
}
