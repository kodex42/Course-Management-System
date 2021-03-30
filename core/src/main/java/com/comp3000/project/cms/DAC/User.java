package com.comp3000.project.cms.DAC;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

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
    @ManyToMany(mappedBy = "students")
    private List<CourseOffering> takingCourseOfferings = new ArrayList<>();
    @OneToMany(mappedBy = "professor")
    private List<CourseOffering> teachingCourseOfferings = new ArrayList<>();

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
        return takingCourseOfferings;
    }

    public void setTakingCourseOfferings(List<CourseOffering> takingCourseOfferings) {
        this.takingCourseOfferings = takingCourseOfferings;
    }

    public List<CourseOffering> getTeachingCourseOfferings() {
        return teachingCourseOfferings;
    }

    public void setTeachingCourseOfferings(List<CourseOffering> teachingCourseOfferings) {
        this.teachingCourseOfferings = teachingCourseOfferings;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public boolean equals(User user) {
        return Objects.equals(id, user.id);
    }
}
