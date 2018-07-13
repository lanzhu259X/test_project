package com.lanzhu.testwork.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController {

    private static final Logger logger = Logger.getLogger(WelcomeController.class);

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String home() {
        logger.info("welcome to test work!");
        return "index";
    }

}
