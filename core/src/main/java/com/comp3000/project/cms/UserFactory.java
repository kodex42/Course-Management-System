package com.comp3000.project.cms;

import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.DAC.User;
import org.springframework.data.util.Pair;

public abstract class UserFactory<T> {

    abstract public Pair<User, String> createUser(T from);
}
