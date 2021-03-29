package com.comp3000.project.cms.BusinessLogic;

import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.services.Term.TermQueryService;

public class TermManagementBL {
    public static boolean hasOverlappingTerms(Term term, TermQueryService termQueryService) {
        Iterable<Term> overlapTerms = termQueryService.getOverlappingTerms(term.getStartDate(), term.getEndDate());
        int size = 0;
        for (Term t : overlapTerms)
            size += 1;
        return size > 0;
    }
}
