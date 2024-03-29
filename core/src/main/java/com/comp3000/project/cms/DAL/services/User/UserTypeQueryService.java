package com.comp3000.project.cms.DAL.services.User;

import com.comp3000.project.cms.DAO.UserType;
import com.comp3000.project.cms.DAL.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTypeQueryService {
    @Autowired
    private UserTypeRepository userTypeRepository;

    public UserType getByType(String type) {
        return this.userTypeRepository.findByType(type);
    }
}
