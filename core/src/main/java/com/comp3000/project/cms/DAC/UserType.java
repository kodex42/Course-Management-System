package com.comp3000.project.cms.DAC;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserType implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;

    public UserType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public static final UserType STUDENT = new UserType(1, "STUDENT");

    public static final UserType PROFESSOR = new UserType(2, "PROFESSOR");

    public static final UserType ADMIN = new UserType(3, "ADMIN");

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getAuthority() {
        return this.getType();
    }
}
