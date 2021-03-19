package com.comp3000.project.cms.services.Term;

import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.repository.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TermQueryService {
    @Autowired
    private TermRepository termRepository;

    private Iterable<Term> getAll() {
        return this.termRepository.findAll();
    }

    private Optional<Term> getById(Integer id) {
        return this.termRepository.findById(id);
    }
}
