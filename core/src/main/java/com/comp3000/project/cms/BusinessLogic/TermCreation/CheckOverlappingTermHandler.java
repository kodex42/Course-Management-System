package com.comp3000.project.cms.BusinessLogic.TermCreation;

import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.BusinessLogic.TermManagementBL;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.services.Term.TermCommandService;
import com.comp3000.project.cms.services.Term.TermQueryService;

import java.security.InvalidParameterException;

public class CheckOverlappingTermHandler extends Handler<Term> {
    private TermQueryService termQueryService;

    public CheckOverlappingTermHandler(TermQueryService termQueryService) {
        this.termQueryService = termQueryService;
    }

    @Override
    public Status<Term> handle(Term term) {
        if (TermManagementBL.hasOverlappingTerms(termQueryService.getAll(), term))
            return Status.failed(term, "Term with "
                    + term.getStartDate() + " and " + term.getEndDate() +
                    " was not created. Overlapping term exists");

        return Status.ok(term);
    }
}
