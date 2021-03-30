package com.comp3000.project.cms.BLL;

import com.comp3000.project.cms.DAO.User;
import org.springframework.data.util.Pair;

public abstract class UserFactory<T> {

    abstract public Pair<User, String> createUser(T from);
}
