package com.comp3000.project.cms.services;

import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.DAC.UserType;
import com.comp3000.project.cms.repository.UserRepository;
import com.comp3000.project.cms.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * User service, contains User related business logic
 */

@Service
public class UserCommandService {
    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private UserRepository userRepository;

    public Boolean removeUserWithId(Integer id) {
        if (id != 1) { // Only delete if the given id is not the admin user
            userRepository.deleteById(id);
            return userRepository.findById(id).isEmpty();
        }
        return false;
    }
}
