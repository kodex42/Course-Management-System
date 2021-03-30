package com.comp3000.project.cms.web.forms;

import com.comp3000.project.cms.DAO.Term;

public class TermForm extends Term {
    private Integer seasonId;

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public Term toObject() {
        Term term = new Term();
        term.setSeason(this.getSeason());
        term.setStartDate(this.getStartDate());
        term.setEndDate(this.getEndDate());
        term.setRegistrationDeadline(this.getRegistrationDeadline());
        term.setWdnDeadline(this.getWdnDeadline());
        term.setWithdrawalDeadline(this.getWithdrawalDeadline());
        term.setReimbursementDeadline(this.getReimbursementDeadline());
        return term;
    }
}
