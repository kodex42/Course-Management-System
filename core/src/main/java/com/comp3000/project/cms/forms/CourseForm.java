package com.comp3000.project.cms.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

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
public class CourseForm extends Form {
    @NotEmpty(message = "Course Name is required.")
    private String course_name;
    private String[] prerequisites;
    private String[] preclusions;
    @Min(value = 25, message = "Capacity can be no lower than 25 students.")
    @Max(value = 500, message = "Capacity can be no greater than 500 students.")
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
