package com.comp3000.project.cms.DAL.services.Submission;

import com.comp3000.project.cms.DAL.repository.SubmissionRepository;
import com.comp3000.project.cms.DAL.services.CommandService;
import com.comp3000.project.cms.DAO.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionCommandService extends CommandService {
    @Autowired
    private SubmissionRepository submissionRepository;

    public Submission create(Submission submission) {
        return submissionRepository.save(submission);
    }
}
