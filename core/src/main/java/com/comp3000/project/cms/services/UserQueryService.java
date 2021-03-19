package com.comp3000.project.cms.services;

import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.DAC.UserType;
import com.comp3000.project.cms.repository.UserRepository;
import com.comp3000.project.cms.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class UserQueryService implements UserDetailsService {
    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public Iterable<User> loadAllUsers() {
        return userRepository.findAll();
    }

    public Iterable<UserType> loadAllUserTypes() {
        return userTypeRepository.findAll();
    }

    public Optional<User> loadUserById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> loadAllUsersOfType(String user_type) {
        UserType userType = userTypeRepository.findByType(user_type);

        return userRepository.findAllByUserType(userType);
    }
}