package com.comp3000.project.cms.DAO;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private User student;
    @OneToOne
    @JoinColumn(name = "deliverable_id", referencedColumnName = "id")
    private Deliverable deliverable;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date submissionDttm;
    private String filename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Deliverable getDeliverable() {
        return deliverable;
    }

    public void setDeliverable(Deliverable deliverable) {
        this.deliverable = deliverable;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getSubmissionDttm() {
        return submissionDttm;
    }

    public void setSubmissionDttm(Date submissionDttm) {
        this.submissionDttm = submissionDttm;
    }
}
