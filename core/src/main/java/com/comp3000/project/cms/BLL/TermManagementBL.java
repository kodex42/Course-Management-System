package com.comp3000.project.cms.BLL;

import com.comp3000.project.cms.DAO.Term;

public class TermManagementBL {
    public static boolean hasOverlappingTerms(Iterable<Term> allTerms, Term term) {
        for (Term t : allTerms)
            if (term.getStartDate().after(t.getStartDate()) && term.getStartDate().before(t.getEndDate()))
                return true;
        return false;
    }
}
