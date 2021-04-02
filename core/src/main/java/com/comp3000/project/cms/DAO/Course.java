package com.comp3000.project.cms.DAO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;
    private String description;
    @ManyToMany
    @JoinTable(
            name = "course_prerequisites",
            joinColumns = { @JoinColumn(name="course_id") },
            inverseJoinColumns = { @JoinColumn(name = "prereq_id")}
    )
    private List<Course> prerequisites = new ArrayList<>();
    @ManyToMany(mappedBy = "prerequisites")
    private List<Course> prerequisiteOf = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "course_preclusions",
            joinColumns = { @JoinColumn(name="course_id") },
            inverseJoinColumns = { @JoinColumn(name = "precl_id")}
    )
    private List<Course> preclusions = new ArrayList<>();
    @ManyToMany(mappedBy = "preclusions")
    private List<Course> preclusionOf = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public List<Course> getPrerequisiteOf() {
        return prerequisiteOf;
    }

    public void setPrerequisiteOf(List<Course> prerequisiteOf) {
        this.prerequisiteOf = prerequisiteOf;
    }

    public List<Course> getPreclusions() {
        return preclusions;
    }

    public void setPreclusions(List<Course> preclusions) {
        this.preclusions = preclusions;
    }

    public List<Course> getPreclusionOf() {
        return preclusionOf;
    }

    public void setPreclusionOf(List<Course> preclusionOf) {
        this.preclusionOf = preclusionOf;
    }

    @Override
    public String toString() {
        return code + " " + name;
    }
}
