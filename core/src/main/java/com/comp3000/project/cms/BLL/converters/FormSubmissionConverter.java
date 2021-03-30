package com.comp3000.project.cms.BLL.converters;

import com.comp3000.project.cms.DAO.Submission;
import com.comp3000.project.cms.web.forms.SubmissionForm;
import org.springframework.stereotype.Service;

@Service
public class FormSubmissionConverter implements Converter<SubmissionForm, Submission> {
    public Submission covert(SubmissionForm d) throws Exception {
        Submission submission = new Submission();
        submission.setId(d.getId());
        submission.setFilename(d.getFilename());
        submission.setStudent(d.getStudent());
        submission.setSubmissionDttm(d.getSubmissionDttm());
        return submission;
    }
}
