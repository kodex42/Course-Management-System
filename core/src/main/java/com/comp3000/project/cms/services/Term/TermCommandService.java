package com.comp3000.project.cms.services.Term;

import com.comp3000.project.cms.BusinessLogic.BusinessLogicHandlerFactory;
import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.Season;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.exception.CannotCreateException;
import com.comp3000.project.cms.forms.TermForm;
import com.comp3000.project.cms.repository.TermRepository;
import com.comp3000.project.cms.services.SeasonQueryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TermCommandService {
    @Autowired
    private TermRepository termRepository;
    @Autowired
    private SeasonQueryService seasonQueryService;
    @Autowired
    private BusinessLogicHandlerFactory factory;

    private void secureCreateTerm(Term term) {
        this.termRepository.save(term);
    }

    public void createTerm(TermForm term) throws NotFoundException, CannotCreateException {
        Term newTerm = term.toObject();
        Season season = seasonQueryService.getById(term.getSeasonId());
        newTerm.setSeason(season);

        Handler<Term> termCreationHandlerChain = factory.createTermCreationHandler();
        Status<Term> status = termCreationHandlerChain.handle(newTerm);

        if (status.isSuccessful())
            secureCreateTerm(newTerm);
        else
            throw new CannotCreateException("Cannot create term: " + status.getError());
    }
}
