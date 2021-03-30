package com.comp3000.project.cms.BusinessLogic;

import com.comp3000.project.cms.DAC.Term;

public class TermManagementBL {
    public static boolean hasOverlappingTerms(Iterable<Term> allTerms, Term term) {
        for (Term t : allTerms)
            if (term.getStartDate().after(t.getStartDate()) && term.getStartDate().before(t.getEndDate()))
                return true;
        return false;
    }
}
