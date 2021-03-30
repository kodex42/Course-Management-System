package com.comp3000.project.cms.DAL.services.Term;

import com.comp3000.project.cms.DAO.Term;
import com.comp3000.project.cms.DAL.repository.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TermCommandService {
    @Autowired
    private TermRepository termRepository;

    public Term createTerm(Term term) {
        return this.termRepository.save(term);
    }
}
