package com.comp3000.project.cms.services.User;

import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.DAC.UserType;
import com.comp3000.project.cms.repository.UserRepository;
import com.comp3000.project.cms.repository.UserTypeRepository;
import javassist.NotFoundException;
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

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Iterable<UserType> getAllUserTypes() {
        return userTypeRepository.findAll();
    }

    public User getById(Integer id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with specified ID was not found"));
    }

    public List<User> getAllUsersOfType(String user_type) {
        UserType userType = userTypeRepository.findByType(user_type);

        return userRepository.findAllByUserType(userType);
    }

    public User getByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
