package com.comp3000.project.cms.DAO;

import javax.persistence.*;

@Entity
@Table(name="course_offr_x_student")
public class CourseOffrStudentEntry {

    private static final float[] LETTER_GRADE_THRESHOLDS = {90f, 85f, 80f, 77f, 73f, 70f, 67f, 63f, 60f, 57f, 53f, 50f, 0f};
    private static final String[] LETTER_GRADES = {"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F"};

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
    private String letterGrade;

    public CourseOffrStudentEntry() {}

    public CourseOffrStudentEntry(CourseOffering courseOffering, User student){
        id = new CourseOffrStudentKey(courseOffering.getId(), student.getId());

        this.courseOffering = courseOffering;
        this.student = student;
    }

    private void computeLetterGrade() {
        for (int i = 0; i < LETTER_GRADE_THRESHOLDS.length; i++) {
            float f = LETTER_GRADE_THRESHOLDS[i];
            if (grade > f) {
                letterGrade = LETTER_GRADES[i];
                break;
            }
        }
    }

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
        if (grade == -1f) {
            this.grade = 0f;
            letterGrade = "WDN";
        } else {
            this.grade = grade;
            computeLetterGrade();
        }
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }
}
