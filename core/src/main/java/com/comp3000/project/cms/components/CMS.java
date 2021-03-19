package com.comp3000.project.cms.components;

import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.SecurityDemoController;
import com.comp3000.project.cms.repository.TermRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Optional;

@Component
public class CMS {
    private static final Logger log = LoggerFactory.getLogger(SecurityDemoController.class);
    @Autowired
    private TermRepository termRepository;

    private Term currentTerm;
    private Date currentTime;

    @PostConstruct
    private void init() {
        loadTermForDate(new java.sql.Date(new Date().getTime()));
    }

    public void loadTermForDate(java.sql.Date d) {
        currentTime = new Date(d.getTime());
        Optional<Term> termOp = termRepository.findTermByDate(d);
        termOp.ifPresent(term -> currentTerm = term);
        log.info("Term loaded as Current: " + currentTerm);
    }

    public Term getCurrentTerm() {
        return currentTerm;
    }

    public Date getCurrentTime() {
        return currentTime;
    }
}
