package com.comp3000.project.cms.controllers;

import com.comp3000.project.cms.forms.LoginForm;
import com.comp3000.project.cms.forms.RegisterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/*  AuthController

    Handles the following routes:
        GET
            /auth/login
            /auth/register
        POST
            /auth/login
            /auth/register
*/
@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        log.info("Login form requested");

        return "login";
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginForm> login(@RequestBody LoginForm loginForm) {
        log.info("Login request received");

        // Authentication service call goes here

        return new ResponseEntity<>(loginForm, HttpStatus.OK);
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        log.info("Registration form requested");

        return "register";
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterForm> register(@RequestBody RegisterForm registerForm) {
        log.info("Registration request received");

        // Registration service call goes here

        return new ResponseEntity<>(registerForm, HttpStatus.OK);
    }
}
