package com.comp3000.project.cms;

import com.comp3000.project.cms.BLL.CourseOffrGradesSubmissionBL;
import com.comp3000.project.cms.DAO.Term;
import junit.framework.TestCase;

import java.sql.Date;

public class CourseOffrGradesSubmissionBLTest extends TestCase {

    Term currentTerm;

    {
        currentTerm = new Term();
        currentTerm.setStartDate(Date.valueOf("2021-01-11"));
        currentTerm.setEndDate(Date.valueOf("2021-04-14"));
    }

    public CourseOffrGradesSubmissionBLTest(String name) {
        super(name);
    }

    public void testIsValidSubmissionPeriod() {
        Date currentTime;

        currentTime = Date.valueOf("2021-04-01");
        assertFalse(CourseOffrGradesSubmissionBL.isValidSubmissionPeriod(currentTerm, currentTime));

        currentTime = Date.valueOf("2021-04-15");
        assertTrue(CourseOffrGradesSubmissionBL.isValidSubmissionPeriod(currentTerm, currentTime));
    }
}
