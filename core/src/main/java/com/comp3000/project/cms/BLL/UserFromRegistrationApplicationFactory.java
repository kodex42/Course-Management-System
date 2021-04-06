package com.comp3000.project.cms.BLL;

import com.comp3000.project.cms.DAL.services.User.UserTypeQueryService;
import com.comp3000.project.cms.DAO.RegApplication;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.DAO.UserType;
import com.comp3000.project.cms.web.config.EncryptionConfig;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class UserFromRegistrationApplicationFactory extends UserFactory<RegApplication>{
    private UserTypeQueryService userTypeQueryService;
    private EncryptionConfig encryptionConfig;

    public UserFromRegistrationApplicationFactory(UserTypeQueryService userTypeQueryService, EncryptionConfig encryptionConfig) {
        this.userTypeQueryService = userTypeQueryService;
        this.encryptionConfig = encryptionConfig;
    }

    @Override
    public Pair<User, String> createUser(RegApplication appl) {
        UserType type;

        if (appl.getBirthDate() == null) {
            type = userTypeQueryService.getByType("PROFESSOR");
        } else {
            type = userTypeQueryService.getByType("STUDENT");
        }

        String pswd = UserPasswordGenerator.generatePassayPassword();
        String password = encryptionConfig.getPassordEncoder().encode(pswd);
        User newUser = new User(appl.getFirstName(), appl.getLastName(),
                appl.getEmail(), password, type, appl.getBirthDate());

        return Pair.of(newUser, pswd);
    }
}
