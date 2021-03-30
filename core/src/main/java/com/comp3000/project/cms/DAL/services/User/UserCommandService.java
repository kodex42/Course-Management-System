package com.comp3000.project.cms.DAL.services.User;

import com.comp3000.project.cms.DAO.RegApplication;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.BLL.UserFactory;
import com.comp3000.project.cms.BLL.UserFromRegistrationApplicationFactory;
import com.comp3000.project.cms.web.config.EncryptionConfig;
import com.comp3000.project.cms.DAL.repository.UserRepository;
import com.comp3000.project.cms.DAL.services.RegApplication.RegApplicationCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

/**
 * User service, contains User related business logic
 */

@Service
public class UserCommandService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTypeQueryService userTypeQueryService;
    @Autowired
    private EncryptionConfig encryptionConfig;
    @Autowired
    private RegApplicationCommandService regApplicationCommandService;
    private UserFactory<RegApplication> userFactory = new UserFromRegistrationApplicationFactory();

    public User create(User u) {
        return this.userRepository.save(u);
    }

    public String createFromApplication(RegApplication appl) {

        Pair<User, String> pair = userFactory.createUser(appl);

        this.create(pair.getFirst());
        regApplicationCommandService.deleteById(appl.getId());
        return pair.getSecond();
    }

    public void delete(User user) {
        userRepository.deleteById(user.getId());
    }
}
