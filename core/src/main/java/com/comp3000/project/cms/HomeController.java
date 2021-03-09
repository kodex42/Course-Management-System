package com.comp3000.project.cms;

import com.comp3000.project.cms.Repository.UserTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private UserTypeRepository userRepository;

    @GetMapping("/index")
    public String index(@RequestParam(name = "name", required = false, defaultValue = "YourNameHere") String name, Model model) {
        log.info("Request: index");

        model.addAttribute("name", name);
        model.addAttribute("userTypes", userRepository.findAll());
        return "index";
    }
}
