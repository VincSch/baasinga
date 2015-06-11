package com.stroodel.baasinga.web.controller;

import com.stroodel.baasinga.service.compile.CompileUtility;
import com.stroodel.baasinga.service.compile.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by vs on 21.05.15.
 */
@Controller
public class HomeController {

    @Autowired
    CompileUtility compileUtility;
    @Autowired
    Terminal terminal;

    @RequestMapping("/")
    public String showHome(ModelMap model) {
        model.addAttribute("title", "Hello world!");
        terminal.mvnCleanInstall("/Users/vs/release/baasinga");
        return "home";
    }

    @RequestMapping("/list")
    public String showList(ModelMap model) {
        model.addAttribute("title", "Hello world!");
        model.addAttribute("title2", "Hello world222222!");
        return "home2";
    }

}
