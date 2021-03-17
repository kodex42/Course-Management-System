package com.comp3000.project.cms.repository;

import com.comp3000.project.cms.DAC.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
