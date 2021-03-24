package com.comp3000.project.cms.forms;

import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.Deliverable;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.DAC.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class DeliverableForm extends Form {
    private String name;
    private MultipartFile file;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date deadline;
    private User author;
    private CourseOffering courseOffr;

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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public CourseOffering getCourseOffr() {
        return courseOffr;
    }

    public void setCourseOffr(CourseOffering courseOffr) {
        this.courseOffr = courseOffr;
    }

    public Deliverable toObject() {
        Deliverable deliverable = new Deliverable();
        deliverable.setDeadline(this.getDeadline());
        deliverable.setCourseOffering(this.getCourseOffr());
        deliverable.setAuthor(this.getAuthor());
        deliverable.setDescription(this.getDescription());
        deliverable.setName(this.getName());

        return deliverable;
    }
}
