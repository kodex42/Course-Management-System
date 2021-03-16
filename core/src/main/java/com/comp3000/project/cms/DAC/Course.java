package com.comp3000.project.cms.DAC;

import javax.persistence.*;
import java.util.Set;

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private Set<Course> prerequisites;
    @ManyToMany(mappedBy = "prerequisites")
    private Set<Course> prerequisiteOf;
    @ManyToMany
    @JoinTable(
            name = "course_preclusions",
            joinColumns = { @JoinColumn(name="course_id") },
            inverseJoinColumns = { @JoinColumn(name = "precl_id")}
    )
    private Set<Course> preclusions;
    @ManyToMany(mappedBy = "preclusions")
    private Set<Course> preclusionOf;

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

    public Set<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(Set<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public Set<Course> getPrerequisiteOf() {
        return prerequisiteOf;
    }

    public void setPrerequisiteOf(Set<Course> prerequisiteOf) {
        this.prerequisiteOf = prerequisiteOf;
    }

    public Set<Course> getPreclusions() {
        return preclusions;
    }

    public void setPreclusions(Set<Course> preclusions) {
        this.preclusions = preclusions;
    }

    public Set<Course> getPreclusionOf() {
        return preclusionOf;
    }

    public void setPreclusionOf(Set<Course> preclusionOf) {
        this.preclusionOf = preclusionOf;
    }
}
