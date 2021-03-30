package com.comp3000.project.cms.web.controllers;

import com.comp3000.project.cms.DAL.services.User.UserCommandService;
import com.comp3000.project.cms.DAL.services.User.UserQueryService;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.exception.CannotDeleteException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

/*  UserController

    Handles the following routes:
        GET
            /user/{user_id}
            /user/users/{user_type}
        DELETE
            /user/{user_id}
*/
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private UserCommandService userCommandService;

    @GetMapping("/{user_id}")
    public String viewUser(@PathVariable Integer user_id,
                           Model model) {
        log.info("User page requested");

        User user;

        try {
            user = userQueryService.getById(user_id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        // Add data to model
        model.addAttribute("user", user);
        return "user";
    }

    @DeleteMapping("/{user_id}")
    public String deleteUser(@PathVariable Integer user_id,
                             Model model) {
        log.info("Request to remove user with id " + user_id + " received.");

        try {
            userCommandService.delete(user_id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (CannotDeleteException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

        return "redirect:/";
    }

    @GetMapping("/list/{user_type}")
    public String listUsersOfType(@PathVariable String user_type,
                            Model model) {
        log.info("User list requested");

        Iterable<User> users = userQueryService.getAllUsersOfType(user_type);

        // Add data to model
        model.addAttribute("type", user_type);
        model.addAttribute("users", users);

        return "user_list";
    }
}
