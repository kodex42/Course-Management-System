package com.comp3000.project.cms.BLL.CourseOffrGradesSubmission;

import com.comp3000.project.cms.BLL.CourseOffrGradesSubmissionBL;
import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.components.CMS;

public class CheckValidGradesSubmissionPeriodHandler extends Handler<CourseOffering> {
    private CMS cms;

    public CheckValidGradesSubmissionPeriodHandler(CMS cms){
        this.cms = cms;
    }

    @Override
    public Status<CourseOffering> handle(CourseOffering courseOffering) {
        if(CourseOffrGradesSubmissionBL.isValidSubmissionPeriod(courseOffering.getTerm(), cms.getCurrentTime()))
            return next != null ? next.handle(courseOffering) : Status.ok(courseOffering);
        return Status.failed(courseOffering, "Course offering term has not yet ended");
    }
}
