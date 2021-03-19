package com.comp3000.project.cms.repository;

import com.comp3000.project.cms.DAC.Term;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.Optional;

public interface TermRepository extends CrudRepository<Term, Integer> {
    @Query("SELECT t FROM Term t WHERE t.startDate <= ?1 AND t.endDate >= ?1")
    Optional<Term> findTermByDate(Date d);
}
