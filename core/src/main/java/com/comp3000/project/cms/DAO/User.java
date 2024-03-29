package com.comp3000.project.cms.DAO;

import com.comp3000.project.cms.BLL.decorators.SubmissionRenderDecorator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private Date birthDate;
    @OneToOne
    private UserType userType;
    @OneToMany(mappedBy = "professor")
    private List<CourseOffering> teachingCourseOfferings = new ArrayList<>();
    @OneToMany(mappedBy = "student")
    private List<CourseOffrStudentEntry> courseOffrStudentEntries = new ArrayList<>();

    public User() {}

    public User(String firstName, String lastName, String username, String password, UserType userType, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.birthDate = birthDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(userType);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public String getAuthority() {
        return userType.getType();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthority(UserType authority) {
        this.userType = authority;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<CourseOffering> getTakingCourseOfferings() {
        return courseOffrStudentEntries.stream().map(CourseOffrStudentEntry::getCourseOffering).collect(Collectors.toList());
    }

    public void addTakingCourseOffering(CourseOffering courseOffering){
        if(courseOffrStudentEntries.stream().anyMatch(e -> e.getCourseOffering().getId().equals(courseOffering.getId()))) return;

        CourseOffrStudentEntry courseOffrStudentEntry = new CourseOffrStudentEntry(courseOffering, this);

        courseOffrStudentEntries.add(courseOffrStudentEntry);
    }

    public List<CourseOffering> getTeachingCourseOfferings() {
        return teachingCourseOfferings;
    }

    public void setTeachingCourseOfferings(List<CourseOffering> teachingCourseOfferings) {
        this.teachingCourseOfferings = teachingCourseOfferings;
    }

    public List<CourseOffrStudentEntry> getCourseOffrStudentEntries() {
        return courseOffrStudentEntries;
    }

    public void setCourseOffrStudentInfos(List<CourseOffrStudentEntry> courseOffrStudentEntries) {
        this.courseOffrStudentEntries = courseOffrStudentEntries;
    }

    public Submission getSubmissionForDeliverable(CourseOffering courseOffering, Deliverable deliverable) {
        Submission submission = courseOffering.getDeliverables().stream()
                .filter(del -> del.equals(deliverable))
                .map(del -> del.getSubmissions())
                .reduce(new ArrayList<>(), (acc, subList) -> {
                    acc.addAll(subList);
                    return acc;
                })
                .stream()
                .filter(sub -> sub.getStudent().equals(this))
                .findFirst()
                .orElse(null);

        if (submission != null)
            return new SubmissionRenderDecorator(submission);

        return submission;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof User) {
            User other = (User)obj;
            return this.getId().equals(other.getId());
        }

        return false;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + userType;
    }
}
