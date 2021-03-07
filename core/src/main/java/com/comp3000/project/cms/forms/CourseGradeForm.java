package com.comp3000.project.cms.forms;

/* Read-Only POJO for controllers
{
        "professor_first_name" : "fname",
        "professor_last_name" : "lname",
        "course_name" : "cname",
        "student_first_name" : "fname",
        "student_last_name" : "lname",
        "grade" : 100.0
}
*/
public class CourseGradeForm {
    private String professor_first_name;
    private String professor_last_name;
    private String course_name;
    private String student_first_name;
    private String student_last_name;
    private float grade;

    public String getProfessor_first_name() {
        return professor_first_name;
    }

    public String getProfessor_last_name() {
        return professor_last_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getStudent_first_name() {
        return student_first_name;
    }

    public String getStudent_last_name() {
        return student_last_name;
    }

    public float getGrade() {
        return grade;
    }
}
