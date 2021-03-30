package com.comp3000.project.cms.web.forms;

import com.comp3000.project.cms.DAO.Deliverable;
import com.comp3000.project.cms.DAO.Submission;
import org.springframework.web.multipart.MultipartFile;

public class SubmissionForm extends Submission {
    private MultipartFile file;

    public SubmissionForm() {
    }

    public SubmissionForm(Submission d) {
        this.setId(d.getId());
        this.setFilename(d.getFilename());
        this.setStudent(d.getStudent());
        this.setSubmissionDttm(d.getSubmissionDttm());
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
