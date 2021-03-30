package com.comp3000.project.cms.BLL.TermCreation;

import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.BLL.TermManagementBL;
import com.comp3000.project.cms.DAO.Term;
import com.comp3000.project.cms.DAL.services.Term.TermQueryService;

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
