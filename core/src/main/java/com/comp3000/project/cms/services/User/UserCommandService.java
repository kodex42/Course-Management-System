package com.comp3000.project.cms.services.User;

import com.comp3000.project.cms.BusinessLogic.BusinessLogicHandlerFactory;
import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.DAC.UserType;
import com.comp3000.project.cms.UserFactory;
import com.comp3000.project.cms.UserFromRegistrationApplicationFactory;
import com.comp3000.project.cms.config.EncryptionConfig;
import com.comp3000.project.cms.exception.CannotDeleteException;
import com.comp3000.project.cms.repository.UserRepository;
import com.comp3000.project.cms.services.RegApplication.RegApplicationCommandService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

/**
 * User service, contains User related business logic
 */

@Service
public class UserCommandService {

    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTypeQueryService userTypeQueryService;
    @Autowired
    private EncryptionConfig encryptionConfig;
    @Autowired
    private RegApplicationCommandService regApplicationCommandService;
    private UserFactory<RegApplication> userFactory = new UserFromRegistrationApplicationFactory();
    @Autowired
    private BusinessLogicHandlerFactory factory;

    private void secureDelete(User user) {
        userRepository.deleteById(user.getId());
    }

    public User create(User u) {
        return this.userRepository.save(u);
    }

    public String createFromApplication(RegApplication appl) {

        Pair<User, String> pair = userFactory.createUser(appl);

        this.create(pair.getFirst());
        regApplicationCommandService.deleteById(appl.getId());
        return pair.getSecond();
    }

    public void delete(Integer user_id) throws NotFoundException, CannotDeleteException {
        User user = userQueryService.getById(user_id);

        Handler<User> deletionHandlerChain = factory.createUserDeletionHandler();
        Status<User> status = deletionHandlerChain.handle(user);

        if (status.isSuccessful())
            secureDelete(user);
        else
            throw new CannotDeleteException("Cannot delete user: " + status.getError());
    }
}
