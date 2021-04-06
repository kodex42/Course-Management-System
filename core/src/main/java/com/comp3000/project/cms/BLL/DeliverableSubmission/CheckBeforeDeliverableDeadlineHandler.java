package com.comp3000.project.cms.BLL.DeliverableSubmission;

import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAO.Submission;
import com.comp3000.project.cms.components.CMS;

public class CheckBeforeDeliverableDeadlineHandler extends Handler<Submission> {
    private CMS cms;

    public CheckBeforeDeliverableDeadlineHandler(CMS cms) {
        this.cms = cms;
    }

    @Override
    public Status<Submission> handle(Submission submission) {
        if (!cms.getCurrentTime().after(submission.getDeliverable().getDeadline()))
            return next != null ? next.handle(submission) : Status.ok(submission);
        return Status.failed(submission, "Submission is past due and will not be accepted");
    }
}
