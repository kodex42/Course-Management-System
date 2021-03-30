package com.comp3000.project.cms.BusinessLogic;

import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.DAC.User;

public class UserManagementBL {

    public static boolean userExists(Iterable<User> allUsers, String username) {
        for (User u : allUsers)
            if (u.getUsername().equals(username))
                return true;
        return false;
    }

    public static boolean userApplicationInProgress(Iterable<RegApplication> allApplications, String email) {
        for (RegApplication a : allApplications)
            if (a.getEmail().equals(email))
                return true;
        return false;
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
