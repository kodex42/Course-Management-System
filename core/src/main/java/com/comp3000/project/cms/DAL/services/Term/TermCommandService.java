package com.comp3000.project.cms.DAL.services.Term;

import com.comp3000.project.cms.BLL.BusinessLogicHandlerFactory;
import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAL.services.SeasonQueryService;
import com.comp3000.project.cms.DAO.Season;
import com.comp3000.project.cms.DAO.Term;
import com.comp3000.project.cms.DAL.repository.TermRepository;
import com.comp3000.project.cms.exception.CannotCreateException;
import com.comp3000.project.cms.web.forms.TermForm;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
