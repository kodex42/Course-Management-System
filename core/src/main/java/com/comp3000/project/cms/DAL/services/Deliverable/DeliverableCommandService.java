package com.comp3000.project.cms.DAL.services.Deliverable;

import com.comp3000.project.cms.DAO.Deliverable;
import com.comp3000.project.cms.DAL.repository.DeliverableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliverableCommandService {
    @Autowired
    private DeliverableRepository deliverableRepository;

    public Deliverable create(Deliverable deliverable) {
        return this.deliverableRepository.save(deliverable);
    }

    public void delete(Integer id) {
        this.deliverableRepository.deleteById(id);
    }
}
