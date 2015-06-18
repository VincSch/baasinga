package com.stroodel.baasinga.web.controller;

import com.stroodel.baasinga.repository.compile.CompileUtility;
import com.stroodel.baasinga.repository.compile.FreeMarkerRenderer;
import com.stroodel.baasinga.repository.compile.Terminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;

/**
 * Created by vs on 21.05.15.
 */
@Controller
public class HomeController {

    private final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    CompileUtility compileUtility;
    @Autowired
    Terminal terminal;
    @Autowired
    FreeMarkerRenderer freeMarkerRenderer;

    @RequestMapping("/")
    public String showHome(ModelMap model) {
        model.addAttribute("title", "Prototyp Maven CLI Tests");
        return "home";
    }

    @RequestMapping("/list")
    public String compile(ModelMap model) {
        model.addAttribute("title", "Hello world!");
        model.addAttribute("title2", "Hello world222222!");
        return "home2";
    }

    @RequestMapping("/compile")
    public String compile(){
        terminal.mvn("/Users/vs/release/baasinga/web");
        return "home";
    }

    @Autowired
    ServletContext servletContext;

    @RequestMapping("/freemarker")
    public String freemarker(){
        //LOG.info(servletContext.getServerInfo());
        String fullPath = servletContext.getRealPath("/WEB-INF/classes");
        freeMarkerRenderer.renderClass(fullPath);
        return "home";
    }

}
