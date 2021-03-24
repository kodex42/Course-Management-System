package com.comp3000.project.cms.services.Deliverable;

import com.comp3000.project.cms.DAC.Deliverable;
import com.comp3000.project.cms.repository.DeliverableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliverableCommandService {
    @Autowired
    private DeliverableRepository deliverableRepository;

    public Deliverable create(Deliverable deliverable) {
        return this.deliverableRepository.save(deliverable);
    }
}
