package com.comp3000.project.cms.DAC;

import com.comp3000.project.cms.DAC.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String password;
    private String email;
    @OneToOne
    private UserType userType;
    @ManyToMany
    @JoinTable(
            name = "student_x_course",
            joinColumns = { @JoinColumn(name="stud_id") },
            inverseJoinColumns = { @JoinColumn(name = "course_id")}
    )
    private Set<CourseOffering> courses = new HashSet<>();

    public User() {

    }

    public User(Integer id, String password, String email, UserType userType) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.userType = userType;
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
        return this.email;
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

    public String getEmail() {
        return email;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAuthority(UserType authority) {
        this.userType = authority;
    }
}
