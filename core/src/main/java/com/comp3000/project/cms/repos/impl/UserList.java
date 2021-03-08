package com.comp3000.project.cms.repos.impl;

import com.comp3000.project.cms.domain.Authority;
import com.comp3000.project.cms.domain.User;
import com.comp3000.project.cms.repos.UserRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * In memory List based implementation of User repository
 */

@Repository
public class UserList implements UserRepo {

    private static Long USER_COUNT;

    private ArrayList<User> users = new ArrayList<>();

    {
        USER_COUNT = (long)0;

        users.add(
            new User(
                USER_COUNT++,
                "admin",
                "$2a$10$aY1CHojFgB2k6HmO2gZiAun4dn4yb.VqQmVZ2CxfYkGK2.gZ.jMu6", // bcrypt -> admin
                "admin@mail.com",
                Authority.ADMIN
            )
        );
        users.add(
            new User(
                USER_COUNT++,
                "student",
                "$2a$10$gFhrK/bUuf7JrHYLJRaLI.g7h9QdH0k/miv2uUuzkSkt6HDSqMAoe", // bcrypt -> student
                "student@mail.com",
                Authority.STUDENT
            )
        );
        users.add(
            new User(
                USER_COUNT++,
                "professor",
                "$2a$10$ThbapmPJHqYG4AqBPyeiQ.5fOU7fU3Tp6Lc5LNslMZzGjcx9kh4Iu", // bcrypt -> professor
                "professor@mail.com",
                Authority.PROF
            )
        );
    }

    @Override
    public User findByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findAny().orElse(null);
    }


}
