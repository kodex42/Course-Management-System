package com.comp3000.project.cms.DAL.repository;

import com.comp3000.project.cms.DAO.UserType;
import org.springframework.data.repository.CrudRepository;

public interface UserTypeRepository extends CrudRepository<UserType, Integer> {
    UserType findByType(String type);
}



