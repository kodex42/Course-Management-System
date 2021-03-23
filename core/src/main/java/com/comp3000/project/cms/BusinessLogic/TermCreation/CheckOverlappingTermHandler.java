package com.comp3000.project.cms.BusinessLogic.TermCreation;

import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.services.Term.TermCommandService;
import com.comp3000.project.cms.services.Term.TermQueryService;

import java.security.InvalidParameterException;

public class CheckOverlappingTermHandler extends Handler<Term> {
    private TermQueryService termQueryService;
    private TermCommandService termCommandService;

    public CheckOverlappingTermHandler(TermQueryService query, TermCommandService command) {
        this.termQueryService = query;
        this.termCommandService = command;
    }

    @Override
    public Status<Term> handle(Term term) {
        try {
            Iterable<Term> overlapTerms = this.termQueryService
                    .getOverlappingTerms(term.getStartDate(), term.getEndDate());
            int size = 0;
            for (Term t : overlapTerms)
                size += 1;

            if (size > 0)
                throw new InvalidParameterException();

            termCommandService.createTerm(term);
        } catch (Exception e) {
            return Status.failed(term, "Term with "
                    + term.getStartDate() + " and " + term.getEndDate() +
                    " was not created. Overlapping term exists");
        }

        return Status.ok(term);
    }
}
