package com.comp3000.project.cms.DAO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CourseOffering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Course course;
    @OneToOne
    private Term term;
    @ManyToOne
    private User professor;
    @ManyToMany
    @JoinTable(
            name = "course_offr_x_student",
            joinColumns = { @JoinColumn(name="course_offr_id") },
            inverseJoinColumns = { @JoinColumn(name = "stud_id")}
    )
    private List<User> students = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "course_offr_id")
    private List<Deliverable> deliverables;

    public List<Deliverable> getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(List<Deliverable> deliverables) {
        this.deliverables = deliverables;
    }

    private Integer capacity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getProfessor() {
        return professor;
    }

    public void setProfessor(User professor) {
        this.professor = professor;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        if (this.course != null && this.term != null) {
            return this.course.getCode() + " "
                    + this.term.toString();
        }
        return "UNKNOWN " + this.getId();
    }
}
