package com.comp3000.project.cms.BusinessLogic;

import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.Term;

import java.sql.Date;

public class CourseDroppingBL {

    public static boolean courseOfferingWithinCurrentTerm(CourseOffering courseOffering, Term current) {
        return courseOffering.getTerm().compareTo(current) == 0;
    }

    public static boolean isValidWithdrawalPeriod(Date currentTime, Term currentTerm) {
        return currentTime.before(currentTerm.getWithdrawalDeadline());
    }
}
