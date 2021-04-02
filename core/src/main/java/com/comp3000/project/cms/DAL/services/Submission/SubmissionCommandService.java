package com.comp3000.project.cms.DAL.services.Submission;

import com.comp3000.project.cms.DAL.repository.SubmissionRepository;
import com.comp3000.project.cms.DAO.Submission;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionCommandService {
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private SubmissionQueryService submissionQueryService;

    public Submission create(Submission submission) {
        return submissionRepository.save(submission);
    }

    public Submission updateGrade(Integer subId, float grade) throws NotFoundException {
        Submission submission = this.submissionQueryService.getById(subId);
        submission.setGrade(grade);

        return this.submissionRepository.save(submission);
    }
}
