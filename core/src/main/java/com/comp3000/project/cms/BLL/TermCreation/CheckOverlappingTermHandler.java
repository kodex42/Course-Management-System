package com.comp3000.project.cms.BLL.TermCreation;

import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAO.Term;
import com.comp3000.project.cms.DAL.services.Term.TermCommandService;
import com.comp3000.project.cms.DAL.services.Term.TermQueryService;

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
