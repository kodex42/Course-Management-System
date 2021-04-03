package com.comp3000.project.cms.DAL.services.Submission;

import com.comp3000.project.cms.BLL.BusinessLogicHandlerFactory;
import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAL.repository.SubmissionRepository;
import com.comp3000.project.cms.DAO.Submission;
import com.comp3000.project.cms.exception.CannotCreateException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionCommandService {
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private SubmissionQueryService submissionQueryService;
    @Autowired
    private BusinessLogicHandlerFactory factory;

    private void secureCreate(Submission submission) {
        submissionRepository.save(submission);
    }

    public void create(Submission submission) throws CannotCreateException {
        Handler<Submission> submissionHandler = factory.createSubmissionHandler();
        Status<Submission> status = submissionHandler.handle(submission);

        if (status.isSuccessful())
            secureCreate(submission);
        else
            throw new CannotCreateException(status.getError());
    }

    public Submission updateGrade(Integer subId, float grade) throws NotFoundException {
        Submission submission = this.submissionQueryService.getById(subId);
        submission.setGrade(grade);

        return this.submissionRepository.save(submission);
    }
}
