package com.comp3000.project.cms.services.Term;

import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.repository.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TermQueryService {
    @Autowired
    private TermRepository termRepository;

    public Iterable<Term> getAll() {
        return this.termRepository.findAll();
    }

    public Optional<Term> getById(Integer id) {
        return this.termRepository.findById(id);
    }

    public Iterable<Term> getOverlappingTerms(Date start, Date end) {
        return this.termRepository.findByStartDateLessThanEqualAndEndDateIsGreaterThanEqual(start, end);
    }
}
