package com.comp3000.project.cms.BLL;

import com.comp3000.project.cms.DAO.RegApplication;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.DAO.UserType;
import com.comp3000.project.cms.web.config.EncryptionConfig;
import com.comp3000.project.cms.DAL.services.User.UserTypeQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;

public class UserFromRegistrationApplicationFactory extends UserFactory<RegApplication>{
    @Autowired
    private UserTypeQueryService userTypeQueryService;
    @Autowired
    private EncryptionConfig encryptionConfig;
    private String pswd = "MY_COOL_PASSWORD_COMP3004";

    @Override
    public Pair<User, String> createUser(RegApplication appl) {
        UserType type;

        if (appl.getBirthDate() == null) {
            type = userTypeQueryService.getByType("PROFESSOR");
        } else {
            type = userTypeQueryService.getByType("STUDENT");
        }

        String password = encryptionConfig.getPassordEncoder().encode(pswd);
        User newUser = new User(appl.getFirstName(), appl.getLastName(),
                appl.getEmail(), password, type, appl.getBirthDate());

        return Pair.of(newUser, pswd);
    }
}
