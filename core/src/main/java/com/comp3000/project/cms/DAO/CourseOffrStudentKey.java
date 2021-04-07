package com.comp3000.project.cms.DAO;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class CourseOffrStudentKey implements Serializable {

    @Column(name = "course_offr_id")
    private Integer courseOffrId;

    @Column(name = "stud_id")
    private Integer studentId;

    public CourseOffrStudentKey() {}

    public CourseOffrStudentKey(Integer courseOffrId, Integer studentId){
        this.courseOffrId = courseOffrId;
        this.studentId = studentId;
    }

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

    @Override
    public int hashCode() {
        return courseOffrId.hashCode() + studentId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof CourseOffrStudentKey)) return false;

        return ((CourseOffrStudentKey)obj).courseOffrId.equals(courseOffrId) && ((CourseOffrStudentKey)obj).studentId.equals(studentId);
    }
}
