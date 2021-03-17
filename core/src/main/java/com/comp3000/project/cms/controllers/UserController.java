package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.services.UserCommandService;
import com.comp3000.project.cms.services.UserQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/*  AdminController

    Handles the following routes:
        GET
            /user/{user_id}
            /user/users/{user_type}
*/
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private UserCommandService userCommandService;

    @GetMapping("/{user_id}")
    public String viewUser(@PathVariable Integer user_id,
                           Model model) {
        log.info("User page requested");

        Optional<User> user = userQueryService.loadUserById(user_id);

        // Add data to model
        if (user.isPresent())
            model.addAttribute("user", user.get());
        else {
            log.info("Requested user was not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return "user";
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity deleteUser(@PathVariable Integer user_id,
                                     Model model) {
        log.info("Request to remove user with id " + user_id + " received.");

        boolean result = userCommandService.removeUserWithId(user_id);

        return result ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/list/{user_type}")
    public String listUsersOfType(@PathVariable String user_type,
                            Model model) {
        log.info("User list requested");

        List<User> users = userQueryService.loadAllUsersOfType(user_type);

        // Add data to model
        model.addAttribute("type", user_type);
        model.addAttribute("users", users);

        return "user_list";
    }
}
