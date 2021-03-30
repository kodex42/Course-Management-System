package com.comp3000.project.cms.BLL.converters;

import com.comp3000.project.cms.DAO.Deliverable;
import com.comp3000.project.cms.web.forms.DeliverableForm;
import org.springframework.stereotype.Service;

@Service
public class FormDeliverableConverter implements Converter<DeliverableForm, Deliverable> {
    public Deliverable covert(DeliverableForm t) throws Exception {
        Deliverable deliverable = new Deliverable();
        deliverable.setId(t.getId());
        deliverable.setDeadline(t.getDeadline());
        deliverable.setCourseOffering(t.getCourseOffr());
        deliverable.setAuthor(t.getAuthor());
        deliverable.setDescription(t.getDescription());
        deliverable.setName(t.getName());
        deliverable.setAuthor(t.getCourseOffr().getProfessor());
        deliverable.setFilename(t.getFilename() == "" ? null : t.getFilename());
        return deliverable;
    }
}
