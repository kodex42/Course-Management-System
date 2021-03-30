package com.comp3000.project.cms.web.forms;

import com.comp3000.project.cms.DAO.Deliverable;
import org.springframework.web.multipart.MultipartFile;


public class DeliverableForm extends Deliverable {
    private MultipartFile file;

    public DeliverableForm() {
    }

    public DeliverableForm(Deliverable d) {
        this.setId(d.getId());
        this.setDeadline(d.getDeadline());
        this.setCourseOffr(d.getCourseOffr());
        this.setAuthor(d.getAuthor());
        this.setDescription(d.getDescription());
        this.setName(d.getName());
        this.setFilename(d.getFilename());
    }

    public MultipartFile getFile() {
        return file;
    }

    public boolean hasFile() {
        return !file.isEmpty();
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
