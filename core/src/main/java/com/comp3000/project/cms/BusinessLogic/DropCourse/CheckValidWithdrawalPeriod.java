package com.comp3000.project.cms.BusinessLogic.DropCourse;

import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.components.CMS;
import org.springframework.data.util.Pair;

import java.sql.Date;

public class CheckValidWithdrawalPeriod extends Handler<Pair<CourseOffering, User>> {
    private CMS cms;

    public CheckValidWithdrawalPeriod(CMS cms) {
        this.cms = cms;
    }

    @Override
    public Status<Pair<CourseOffering, User>> handle(Pair<CourseOffering, User> studentRegisteredCourseRelationship) {
        Date withdrawalDeadline = cms.getCurrentTerm().getWithdrawalDeadline();
        Date serverTime = cms.getCurrentTime();
        if (serverTime.before(withdrawalDeadline))
            return next.handle(studentRegisteredCourseRelationship);
        return Status.failed(studentRegisteredCourseRelationship, "Withdrawal deadline exceeded, course withdrawal not possible");
    }
}
