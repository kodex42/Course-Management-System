package com.comp3000.project.cms.BLL.decorators;

import com.comp3000.project.cms.DAO.Submission;

import java.util.concurrent.TimeUnit;

public class SubmissionRenderDecorator extends Submission {
    private boolean isLate;
    private long lateSecs;

    public boolean isLate() {
        return isLate;
    }

    public void setLate(boolean late) {
        isLate = late;
    }

    public String getLate() {
        long n = this.lateSecs;

        long days = n / (24 * 3600);
        n = n % (24 * 3600);
        long hours = n / 3600;
        n %= 3600;
        long mins = Math.max(1, n / 60);

        return days + " days " + hours + " hours " + mins + " mins";
    }

    public SubmissionRenderDecorator(Submission v) {
        this.setGrade(v.getGrade());
        this.setDeliverable(v.getDeliverable());
        this.setFilename(v.getFilename());
        this.setSubmissionDttm(v.getSubmissionDttm());
        this.setId(v.getId());
        this.setStudent(v.getStudent());
        this.isLate = v.getSubmissionDttm()
                .compareTo(v.getDeliverable().getDeadline()) > 0;

        if (this.isLate) {
            long diffInMillies = Math.abs(v.getSubmissionDttm().getTime()
                    - v.getDeliverable().getDeadline().getTime());
            this.lateSecs = Math.abs(
                TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS)
            );
        }

    }
}
