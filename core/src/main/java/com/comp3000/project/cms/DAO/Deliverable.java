package com.comp3000.project.cms.DAO;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Deliverable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private CourseOffering courseOffr;

    @OneToOne
    private User author;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date deadline;

    private String description;

    private String filename;

    public CourseOffering getCourseOffr() {
        return courseOffr;
    }

    public void setCourseOffr(CourseOffering courseOffr) {
        this.courseOffr = courseOffr;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CourseOffering getCourseOffering() {
        return courseOffr;
    }

    public void setCourseOffering(CourseOffering courseOffering) {
        this.courseOffr = courseOffering;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
