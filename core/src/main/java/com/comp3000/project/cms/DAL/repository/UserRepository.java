package com.comp3000.project.cms.DAL.repository;

import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.DAO.UserType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    List<User> findAllByUserType(UserType userType);
}
