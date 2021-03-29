package com.comp3000.project.cms.BusinessLogic;

import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.services.RegApplication.RegApplicationQueryService;
import com.comp3000.project.cms.services.User.UserQueryService;

public class UserManagementBL {

    public static boolean userExists(UserQueryService userQueryService, String username) {
        return userQueryService.getByUsername(username) != null;
    }

    public static boolean userApplicationInProgress(RegApplicationQueryService regApplicationQueryService, String email) {
        return regApplicationQueryService.getByEmail(email) == null;
    }

    public static boolean userIsAdmin(User user) {
        return user.getId() == 1;
    }

    public static boolean userNotTakingCourses(User user) {
        return user.getTakingCourseOfferings().isEmpty();
    }

    public static boolean userNotTeachingCourses(User user) {
        return user.getTeachingCourseOfferings().isEmpty();
    }
}
