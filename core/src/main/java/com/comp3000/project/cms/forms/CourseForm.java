package com.comp3000.project.cms.forms;

/* Read-Only POJO for controllers
{
        "course_name" : "cname",
        "prerequisites" : [
            "COMP1234"
        ],
        "preclusions" : [
            "COMP2234"
        ],
        "capacity" : 100
}
*/
public class CourseForm {
    private String course_name;
    private String[] prerequisites;
    private String[] preclusions;
    private int capacity;

    public String getCourse_name() {
        return course_name;
    }

    public String[] getPrerequisites() {
        return prerequisites;
    }

    public String[] getPreclusions() {
        return preclusions;
    }

    public int getCapacity() {
        return capacity;
    }
}
