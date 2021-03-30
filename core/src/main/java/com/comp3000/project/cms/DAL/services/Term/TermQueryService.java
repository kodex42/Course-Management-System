package com.comp3000.project.cms.DAL.services.Term;

import com.comp3000.project.cms.DAO.Term;
import com.comp3000.project.cms.DAL.repository.TermRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TermQueryService {
    @Autowired
    private TermRepository termRepository;

    public Iterable<Term> getAll() {
        return this.termRepository.findAll();
    }

    public Term getById(Integer id) throws NotFoundException {
        return this.termRepository.findById(id).orElseThrow(() -> new NotFoundException("Term with specified ID was not found"));
    }

    public Iterable<Term> getOverlappingTerms(Date start, Date end) {
        return this.termRepository.findByStartDateLessThanEqualAndEndDateIsGreaterThanEqual(start, end);
    }
}
