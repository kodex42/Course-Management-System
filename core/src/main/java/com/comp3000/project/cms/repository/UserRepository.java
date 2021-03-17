package com.comp3000.project.cms.repository;

import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.DAC.UserType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.userType = ?1")
    List<User> findAllByType(UserType userTypeId);
}
