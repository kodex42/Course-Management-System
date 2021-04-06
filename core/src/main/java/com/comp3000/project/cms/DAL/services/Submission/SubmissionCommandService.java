package com.comp3000.project.cms.DAL.services.Submission;

import com.comp3000.project.cms.BLL.BusinessLogicHandlerFactory;
import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAL.repository.SubmissionRepository;
import com.comp3000.project.cms.DAL.services.CommandService;
import com.comp3000.project.cms.DAO.Submission;
import com.comp3000.project.cms.exception.CannotCreateException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionCommandService extends CommandService {
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

    public void updateGrade(Integer subId, Float grade) throws NotFoundException {
        if (grade == null) grade = 0f;
        Submission submission = this.submissionQueryService.getById(subId);
        submission.setGrade(grade);

        this.submissionRepository.save(submission);
    }
}
