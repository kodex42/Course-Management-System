package com.comp3000.project.cms.DAO;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CourseOffrStudentKey implements Serializable {

    @Column(name = "course_offr_id")
    private Integer courseOffrId;

    @Column(name = "stud_id")
    private Integer studentId;

    public Integer getCourseOffrId() {
        return courseOffrId;
    }

    public void setCourseOffrId(Integer courseOffrId) {
        this.courseOffrId = courseOffrId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}
