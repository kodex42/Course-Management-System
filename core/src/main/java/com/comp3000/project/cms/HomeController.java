package com.comp3000.project.cms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/index")
    public String index(@RequestParam(name = "name", required = false, defaultValue = "YourNameHere") String name, Model model) {
        log.info("Request: index");

        model.addAttribute("name", name);
        return "index";
    }
}
