package com.comp3000.project.cms;

import com.comp3000.project.cms.BusinessLogic.UserManagementBL;
import com.comp3000.project.cms.DAC.*;
import junit.framework.TestCase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserManagementBLTest extends TestCase {

    private final String JOHN_USERNAME = "johnd@email.com";
    private final String JANE_USERNAME = "janeb@email.com";
    private final String STAN_USERNAME = "stans@email.com";

    private User admin;
    private User john;
    private User jane;

    private List<User> allUsers;

    private List<RegApplication> allApplications;

    {
        admin = new User("Admin", "Admin", "admin", "admin", UserType.ADMIN, null);
        admin.setId(1);
        john = new User("John", "Doe", JOHN_USERNAME, "student", UserType.STUDENT, Date.valueOf("1999-01-01"));
        john.setId(2);
        jane = new User("Jane", "Boe", JANE_USERNAME, "professor", UserType.PROFESSOR, Date.valueOf("1979-01-01"));
        jane.setId(3);

        CourseOffering courseOffering = new CourseOffering();
        john.setTakingCourseOfferings(Collections.singletonList(courseOffering));
        jane.setTeachingCourseOfferings(Collections.singletonList(courseOffering));

        RegApplication stanApplication = new RegApplication();
        stanApplication.setId(1);
        stanApplication.setFirstName("Stan");
        stanApplication.setLastName("Stewarts");
        stanApplication.setEmail(STAN_USERNAME);
        stanApplication.setBirthDate(Date.valueOf("1998-02-02"));

        allUsers = new ArrayList<>();
        allUsers.add(admin);
        allUsers.add(john);
        allUsers.add(jane);

        allApplications = new ArrayList<>();
        allApplications.add(stanApplication);
    }

    public UserManagementBLTest(String name) {
        super(name);
    }

    public void testUserExists() {
        assertFalse(UserManagementBL.userExists(allUsers, "markw@email.com"));
        assertTrue(UserManagementBL.userExists(allUsers, JANE_USERNAME));
    }

    public void testUserApplicationInProgress() {
        assertFalse(UserManagementBL.userApplicationInProgress(allApplications, JOHN_USERNAME));
        assertTrue(UserManagementBL.userApplicationInProgress(allApplications, STAN_USERNAME));
    }

    public void testUserIsAdmin() {
        assertFalse(UserManagementBL.userIsAdmin(john));
        assertFalse(UserManagementBL.userIsAdmin(jane));
        assertTrue(UserManagementBL.userIsAdmin(admin));
    }

    public void testUserNotTakingCourses() {
        assertTrue(UserManagementBL.userNotTakingCourses(admin));
        assertTrue(UserManagementBL.userNotTakingCourses(jane));
        assertFalse(UserManagementBL.userNotTakingCourses(john));
    }

    public void testUserNotTeachingCourses() {
        assertTrue(UserManagementBL.userNotTeachingCourses(admin));
        assertTrue(UserManagementBL.userNotTeachingCourses(john));
        assertFalse(UserManagementBL.userNotTeachingCourses(jane));
    }
}
