package com.comp3000.project.cms.DAC;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;

@Entity
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToOne
    private Season season;
    private Date startDate;
    private Date endDate;
    private Date registrationDeadline;
    private Date wdnDeadline;
    private Date withdrawalDeadline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(Date registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public Date getWdnDeadline() {
        return wdnDeadline;
    }

    public void setWdnDeadline(Date wdnDeadline) {
        this.wdnDeadline = wdnDeadline;
    }

    public Date getWithdrawalDeadline() {
        return withdrawalDeadline;
    }

    public void setWithdrawalDeadline(Date withdrawalDeadline) {
        this.withdrawalDeadline = withdrawalDeadline;
    }

    @Override
    public String toString() {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        return season.getCode() + c.get(Calendar.YEAR);
    }
}

