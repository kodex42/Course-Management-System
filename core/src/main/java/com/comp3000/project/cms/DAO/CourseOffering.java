package com.comp3000.project.cms.DAO;

import com.comp3000.project.cms.DAL.Visitor.Visitable;
import com.comp3000.project.cms.DAL.Visitor.Visitor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class CourseOffering implements Visitable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Course course;
    @OneToOne
    private Term term;
    @ManyToOne
    private User professor;
    @OneToMany(mappedBy = "courseOffering", cascade = CascadeType.ALL)
    private List<CourseOffrStudentEntry> courseOffrStudentEntries;
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
        return courseOffrStudentEntries.stream().map(CourseOffrStudentEntry::getStudent).collect(Collectors.toList());
    }

    public CourseOffrStudentEntry addStudent(User student){
        if(courseOffrStudentEntries.stream().anyMatch(e -> e.getStudent().getId().equals(student.getId()))) return null;

        CourseOffrStudentEntry courseOffrStudentEntry = new CourseOffrStudentEntry(this, student);
        courseOffrStudentEntries.add(courseOffrStudentEntry);

        return courseOffrStudentEntry;
    }

    public CourseOffrStudentEntry removeStudent(User student){

        CourseOffrStudentEntry courseOffrStudentEntry = courseOffrStudentEntries.stream().filter(e -> e.getStudent().getId().equals(student.getId())).findAny().orElse(null);
        courseOffrStudentEntries.remove(courseOffrStudentEntry);

        return courseOffrStudentEntry;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<CourseOffrStudentEntry> getCourseOffrStudentEntries() {
        return courseOffrStudentEntries;
    }

    public void setCourseOffrStudentEntries(List<CourseOffrStudentEntry> courseOffrStudentEntries) {
        this.courseOffrStudentEntries = courseOffrStudentEntries;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj instanceof CourseOffering) {
            CourseOffering other = (CourseOffering) obj;
            return this.getId().equals(other.getId());
        }

        return false;
    }

    @Override
    public String toString() {
        if (this.course != null && this.term != null) {
            return this.course.getCode() + " "
                    + this.term.toString();
        }
        return "UNKNOWN " + this.getId();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitCourseOffering(this);
    }
}
