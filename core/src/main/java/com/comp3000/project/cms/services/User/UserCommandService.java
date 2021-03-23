package com.comp3000.project.cms.services.User;

import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.DAC.UserType;
import com.comp3000.project.cms.config.EncryptionConfig;
import com.comp3000.project.cms.repository.UserRepository;
import com.comp3000.project.cms.services.RegApplication.RegApplicationCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * User service, contains User related business logic
 */

@Service
public class UserCommandService {
    private String pswd = "MY_COOL_PASSWORD_COMP3004";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTypeQueryService userTypeQueryService;
    @Autowired
    private EncryptionConfig encryptionConfig;
    @Autowired
    private RegApplicationCommandService regApplicationCommandService;

    public ResponseEntity<String> removeUserWithId(Integer id) {
        if (id != 1) { // Only delete if the given id is not the admin user
            userRepository.deleteById(id);
            return userRepository.findById(id).isEmpty() ? new ResponseEntity<>("User with id " + id + " Deleted", HttpStatus.OK) : new ResponseEntity<>("Unable to delete user with id " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Deletion of Admin is Forbidden", HttpStatus.FORBIDDEN);
    }

    public User create(User u) {
        return this.userRepository.save(u);
    }

    public String createFromApplication(RegApplication appl) {
        UserType type;

        if (appl.getBirthDate() == null) {
            type = userTypeQueryService.getByType("PROFESSOR");
        } else {
            type = userTypeQueryService.getByType("STUDENT");
        }

        String password = encryptionConfig.getPassordEncoder().encode(pswd);
        User newUser = new User(appl.getFirstName(), appl.getLastName(),
                appl.getEmail(), password, type, appl.getBirthDate());

        this.create(newUser);
        regApplicationCommandService.deleteById(appl.getId());

        return pswd;
    }
}
