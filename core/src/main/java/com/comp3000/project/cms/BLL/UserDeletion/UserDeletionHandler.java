package com.comp3000.project.cms.BLL.UserDeletion;

import com.comp3000.project.cms.BLL.Handler;
import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.DAL.services.User.UserCommandService;
import com.comp3000.project.cms.DAL.services.User.UserQueryService;
import javassist.NotFoundException;

public class UserDeletionHandler extends Handler<User> {
    private UserCommandService userCommandService;
    private UserQueryService userQueryService;

    public UserDeletionHandler(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @Override
    public Status<User> handle(User user) {
        this.userCommandService.delete(user);

        try {
            this.userQueryService.getById(user.getId());
        } catch (NotFoundException e) {
            return Status.ok(user);
        }

        // Should not be reached unless something went wrong
        return Status.failed(user, "User could not be deleted due to an error");
    }
}
