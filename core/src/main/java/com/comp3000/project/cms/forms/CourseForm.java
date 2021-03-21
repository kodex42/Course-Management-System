package com.comp3000.project.cms.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

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
    @NotEmpty(message = "Course code is required.")
    private String code;
    @NotEmpty(message = "Course name is required.")
    private String name;
    @NotEmpty(message = "Course description is required.")
    private String description;
    private List<String> prerequisites = new ArrayList<>();
    private List<String> preclusions = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public List<String> getPreclusions() {
        return preclusions;
    }

    public void setPreclusions(List<String> preclusions) {
        this.preclusions = preclusions;
    }
}
