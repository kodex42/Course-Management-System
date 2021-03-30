package com.comp3000.project.cms;

import com.comp3000.project.cms.BusinessLogic.CourseDroppingBL;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.Term;
import junit.framework.TestCase;

import java.sql.Date;

public class CourseDroppingBLTest extends TestCase {

    private Date termStart;
    private Date termEnd;
    private Date termWDNDeadline;
    private Date termReimbursementDeadline;
    private Date termWithdrawalDeadline;

    private Term currentTerm;

    {
        termStart = Date.valueOf("2021-01-11");
        termEnd = Date.valueOf("2021-04-14");
        termWDNDeadline = Date.valueOf("2021-01-31");
        termReimbursementDeadline = Date.valueOf("2021-02-10");
        termWithdrawalDeadline = Date.valueOf("2021-02-15");

        currentTerm = new Term();
        currentTerm.setStartDate(termStart);
        currentTerm.setEndDate(termEnd);
        currentTerm.setWdnDeadline(termWDNDeadline);
        currentTerm.setReimbursementDeadline(termReimbursementDeadline);
        currentTerm.setWithdrawalDeadline(termWithdrawalDeadline);
    }

    public CourseDroppingBLTest(String name) {
        super(name);
    }

    public void testCourseOfferingWithinCurrentTerm() {
        Term t = new Term();
        t.setStartDate(Date.valueOf("2021-09-09"));
        CourseOffering courseOffering1 = new CourseOffering();
        courseOffering1.setTerm(t);
        CourseOffering courseOffering2 = new CourseOffering();
        courseOffering2.setTerm(currentTerm);

        assertFalse(CourseDroppingBL.courseOfferingWithinCurrentTerm(courseOffering1, currentTerm));
        assertTrue(CourseDroppingBL.courseOfferingWithinCurrentTerm(courseOffering2, currentTerm));
    }

    public void testIsValidWithdrawalPeriod() {
        assertTrue(CourseDroppingBL.isValidWithdrawalPeriod(termStart, currentTerm));
        assertTrue(CourseDroppingBL.isValidWithdrawalPeriod(termWDNDeadline, currentTerm));
        assertTrue(CourseDroppingBL.isValidWithdrawalPeriod(termReimbursementDeadline, currentTerm));
        assertTrue(CourseDroppingBL.isValidWithdrawalPeriod(termWithdrawalDeadline, currentTerm));
        assertFalse(CourseDroppingBL.isValidWithdrawalPeriod(termEnd, currentTerm));
    }

    public void testComputeReimbursementType() {
        assertEquals(CourseDroppingBL.computeReimbursementType(termStart, currentTerm), CourseDroppingBL.ReimbursementType.FULL_REIMBURSEMENT);
        assertEquals(CourseDroppingBL.computeReimbursementType(termWDNDeadline, currentTerm), CourseDroppingBL.ReimbursementType.FULL_REIMBURSEMENT);
        assertEquals(CourseDroppingBL.computeReimbursementType(termReimbursementDeadline, currentTerm), CourseDroppingBL.ReimbursementType.REIMBURSEMENT_WITH_WDN);
        assertEquals(CourseDroppingBL.computeReimbursementType(termWithdrawalDeadline, currentTerm), CourseDroppingBL.ReimbursementType.WDN_NO_REIMBURSEMENT);
    }
}
