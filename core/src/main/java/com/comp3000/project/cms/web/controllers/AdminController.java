package com.comp3000.project.cms.web.controllers;

import com.comp3000.project.cms.DAL.services.EventLoggerService;
import com.comp3000.project.cms.DAL.services.User.UserQueryService;
import com.comp3000.project.cms.components.EventLoggerPreferences;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private EventLoggerPreferences eventLoggerPreferences;
    @Autowired
    private EventLoggerService eventLoggerService;

    @PostMapping("/set-event-logger-prefs")
    public String setEventLoggerPref(@RequestParam Map<String, String> preferences){
        log.info("Change of event logger preferences requested");

        try{
            eventLoggerPreferences.setPrefs(preferences.values());
        }catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return "redirect:/admin";
    }

}
