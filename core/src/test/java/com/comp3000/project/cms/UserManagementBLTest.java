package com.comp3000.project.cms;

import com.comp3000.project.cms.BLL.UserManagementBL;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.RegApplication;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.DAO.UserType;
import junit.framework.TestCase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserManagementBLTest extends TestCase {

    private final String JOHN_USERNAME = "johnd@email.com";
    private final String JANE_USERNAME = "janeb@email.com";
    private final String STAN_USERNAME = "stans@email.com";

    private UserType adminType;
    private UserType studentType;
    private UserType professorType;

    private User admin;
    private User john;
    private User jane;

    private List<User> allUsers;

    private List<RegApplication> allApplications;

    {
        adminType = new UserType();
        adminType.setId(1);
        adminType.setType("ADMIN");
        studentType = new UserType();
        studentType.setId(2);
        studentType.setType("STUDENT");
        professorType = new UserType();
        professorType.setId(3);
        professorType.setType("PROFESSOR");

        admin = new User("Admin", "Admin", "admin", "admin", adminType, null);
        admin.setId(1);
        john = new User("John", "Doe", JOHN_USERNAME, "student", studentType, Date.valueOf("1999-01-01"));
        john.setId(2);
        jane = new User("Jane", "Boe", JANE_USERNAME, "professor", professorType, Date.valueOf("1979-01-01"));
        jane.setId(3);

        CourseOffering courseOffering = new CourseOffering();
        john.addTakingCourseOffering(courseOffering);
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
