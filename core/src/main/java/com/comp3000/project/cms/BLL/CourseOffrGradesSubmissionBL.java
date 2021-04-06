package com.comp3000.project.cms.BLL;

import com.comp3000.project.cms.DAO.Term;

import java.util.Date;

public class CourseOffrGradesSubmissionBL {

    public static boolean isValidSubmissionPeriod(Term courseOffrTerm, Date currentDate){
        return currentDate.after(courseOffrTerm.getEndDate());
    }

}
