package com.comp3000.project.cms.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * Authority enum, contains possible user authorities
 */

public enum Authority implements GrantedAuthority {
    ADMIN,
    STUDENT,
    PROF;

    @Override
    public String getAuthority(){
        return name();
    }
}
