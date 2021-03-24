package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.BusinessLogic.BusinessLogicHandlerFactory;
import com.comp3000.project.cms.BusinessLogic.Handler;
import com.comp3000.project.cms.BusinessLogic.Status;
import com.comp3000.project.cms.DAC.User;
import com.comp3000.project.cms.services.User.UserCommandService;
import com.comp3000.project.cms.services.User.UserQueryService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
    @Autowired
    private BusinessLogicHandlerFactory factory;
    private Handler<User> userHandler;

    @GetMapping
    public String userRedirect(RedirectAttributes redirectAttributes) {
        return "redirect:/";
    }

    @GetMapping("/{user_id}")
    public String viewUser(@PathVariable Integer user_id,
                           Model model) {
        log.info("User page requested");
        User user;


        User user;

        try {
            user = userQueryService.loadUserById(user_id);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + user_id + " could not be found");
        }

        // Add data to model
        model.addAttribute("user", user);
        return "user";
    }

    @DeleteMapping("/{user_id}")
    public String deleteUser(@PathVariable Integer user_id,
                             RedirectAttributes redirectAttributes) {
        log.info("Request to remove user with id " + user_id + " received.");

        User user;

        // Attempt to fetch user
        try {
            user = userQueryService.getById(user_id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + user_id + " could not be found");
        }

        // Handle user deletion
        userHandler = factory.createUserDeletionHandler();
        Status<User> status = userHandler.handle(user);
        redirectAttributes.addFlashAttribute("status", status);

        return userRedirect(redirectAttributes);
    }

    @GetMapping("/list/{user_type}")
    public String listUsersOfType(@PathVariable String user_type,
                            Model model) {
        log.info("User list requested");

        Iterable<User> users = userQueryService.loadAllUsersOfType(user_type);

        // Add data to model
        model.addAttribute("type", user_type);
        model.addAttribute("users", users);

        return "user_list";
    }
}
