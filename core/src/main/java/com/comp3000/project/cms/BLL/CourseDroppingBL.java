package com.comp3000.project.cms.BLL;

import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.Term;

import java.sql.Date;

public class CourseDroppingBL {

    public enum ReimbursementType {
        FULL_REIMBURSEMENT,
        REIMBURSEMENT_WITH_WDN,
        WDN_NO_REIMBURSEMENT
    }

    public static boolean courseOfferingWithinCurrentTerm(CourseOffering courseOffering, Term current) {
        return courseOffering.getTerm().compareTo(current) == 0;
    }

    public static boolean isValidWithdrawalPeriod(Date currentTime, Term currentTerm) {
        return !currentTime.after(currentTerm.getWithdrawalDeadline());
    }

    public static ReimbursementType computeReimbursementType(Date currentTime, Term currentTerm) {
        if (currentTime.after(currentTerm.getReimbursementDeadline()))
            return ReimbursementType.WDN_NO_REIMBURSEMENT;
        if (currentTime.after(currentTerm.getWdnDeadline()))
            return ReimbursementType.REIMBURSEMENT_WITH_WDN;
        return ReimbursementType.FULL_REIMBURSEMENT;
    }
}
