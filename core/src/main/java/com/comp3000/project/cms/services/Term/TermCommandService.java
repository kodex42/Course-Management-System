package com.comp3000.project.cms.services.Term;

import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.repository.TermRepository;
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
