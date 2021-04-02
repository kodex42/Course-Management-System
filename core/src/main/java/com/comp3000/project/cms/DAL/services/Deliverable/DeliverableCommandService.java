package com.comp3000.project.cms.DAL.services.Deliverable;

import com.comp3000.project.cms.DAL.services.CommandService;
import com.comp3000.project.cms.DAO.Deliverable;
import com.comp3000.project.cms.DAL.repository.DeliverableRepository;
import com.comp3000.project.cms.DAO.Event;
import com.comp3000.project.cms.common.EventType;
import com.comp3000.project.cms.components.CMS;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliverableCommandService extends CommandService {
    @Autowired
    private DeliverableQueryService deliverableQueryService;
    @Autowired
    private DeliverableRepository deliverableRepository;
    @Autowired
    private CMS cms;

    public Deliverable create(Deliverable deliverable) {
        deliverable = deliverableRepository.save(deliverable);

        notifyObservers(new Event(EventType.CREATION, cms.getCurrentTime(), "Deliverable: " + deliverable.toString()));

        return deliverable;
    }

    public void delete(Integer id) throws NotFoundException{
        Deliverable deliverable = deliverableQueryService.getById(id);

        deliverableRepository.delete(deliverable);

        notifyObservers(new Event(EventType.DELETION, cms.getCurrentTime(), "Deliverable: " + deliverable.toString()));
    }
}
