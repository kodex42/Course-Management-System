package com.comp3000.project.cms.repository;

import com.comp3000.project.cms.DAC.UserType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserTypeRepository extends CrudRepository<UserType, Integer> {
    UserType findByType(String type);
}



