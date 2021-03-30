package com.comp3000.project.cms;

import com.comp3000.project.cms.BLL.TermManagementBL;
import com.comp3000.project.cms.DAO.Term;
import junit.framework.TestCase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TermManagementBLTest extends TestCase {

    private List<Term> allTerms;

    {
        Term t1 = new Term();
        t1.setStartDate(Date.valueOf("2020-01-01"));
        t1.setEndDate(Date.valueOf("2020-05-01"));
        Term t2 = new Term();
        t2.setStartDate(Date.valueOf("2020-09-01"));
        t2.setStartDate(Date.valueOf("2020-12-01"));

        allTerms = new ArrayList<>();
        allTerms.add(t1);
        allTerms.add(t2);
    }

    public TermManagementBLTest(String name) {
        super(name);
    }

    public void testHasOverlappingTerms() {
        Term newTerm1 = new Term();
        newTerm1.setStartDate(Date.valueOf("2020-01-11"));
        newTerm1.setEndDate(Date.valueOf("2020-05-11"));
        Term newTerm2 = new Term();
        newTerm2.setStartDate(Date.valueOf("2020-05-11"));
        newTerm2.setEndDate(Date.valueOf("2020-08-11"));

        assertTrue(TermManagementBL.hasOverlappingTerms(allTerms, newTerm1));
        assertFalse(TermManagementBL.hasOverlappingTerms(allTerms, newTerm2));
    }
}
