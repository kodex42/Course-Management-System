package com.comp3000.project.cms.DAL.services.Deliverable;

import com.comp3000.project.cms.DAO.Deliverable;
import com.comp3000.project.cms.DAL.repository.DeliverableRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliverableQueryService {
    @Autowired
    private DeliverableRepository deliverableRepository;

    public Deliverable getById(Integer deliverableId) throws NotFoundException {
        return deliverableRepository.findById(deliverableId).orElseThrow(() ->
                new NotFoundException("Deliverable with specified ID was not found"));
    }
}