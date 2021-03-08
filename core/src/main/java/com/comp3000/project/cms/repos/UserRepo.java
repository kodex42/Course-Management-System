package com.comp3000.project.cms.repos;

import com.comp3000.project.cms.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * User repository interface, defines repository operations
 */

public interface UserRepo //extends CrudRepository<User, Long>{
{

    User findByUsername(String username);

}
