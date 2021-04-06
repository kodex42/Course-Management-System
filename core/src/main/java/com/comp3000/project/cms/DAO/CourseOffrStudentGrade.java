package com.comp3000.project.cms.DAO;

import javax.persistence.*;

@Entity
@Table(name="course_offr_x_student")
public class CourseOffrStudentGrade {

    @EmbeddedId
    private CourseOffrStudentKey id;

    @ManyToOne
    @MapsId("courseOffrId")
    @JoinColumn(name = "course_offr_id")
    private CourseOffering courseOffering;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "stud_id")
    private User student;

    private Float grade;

    public CourseOffrStudentKey getId() {
        return id;
    }

    public void setId(CourseOffrStudentKey id) {
        this.id = id;
    }

    public CourseOffering getCourseOffering() {
        return courseOffering;
    }

    public void setCourseOffering(CourseOffering courseOffering) {
        this.courseOffering = courseOffering;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }
}
