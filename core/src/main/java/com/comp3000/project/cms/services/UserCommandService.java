package com.comp3000.project.cms.services;

import com.comp3000.project.cms.repository.UserRepository;
import com.comp3000.project.cms.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * User service, contains User related business logic
 */

@Service
public class UserCommandService {
    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> removeUserWithId(Integer id) {
        if (id != 1) { // Only delete if the given id is not the admin user
            userRepository.deleteById(id);
            return userRepository.findById(id).isEmpty() ? new ResponseEntity<>("User with id " + id + " Deleted", HttpStatus.OK) : new ResponseEntity<>("Unable to delete user with id " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Deletion of Admin is Forbidden", HttpStatus.FORBIDDEN);
    }
}
