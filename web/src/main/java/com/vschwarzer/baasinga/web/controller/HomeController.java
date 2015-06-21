package com.vschwarzer.baasinga.web.controller;

import com.vschwarzer.baasinga.service.generator.ApplicationGenerator;
import com.vschwarzer.baasinga.service.generator.engine.TemplateRenderer;
import com.vschwarzer.baasinga.service.generator.mvn.MavenCompiler;
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
    TemplateRenderer templateRenderer;
    @Autowired
    MavenCompiler mavenCompiler;
    @Autowired
    ApplicationGenerator applicationGenerator;

    @RequestMapping("/")
    public String showHome(ModelMap model) {
        model.addAttribute("title", "Prototyp Maven CLI Tests");
        return "home";
    }

    @RequestMapping("/list")
    public String list(ModelMap model) {
        model.addAttribute("title", "Hello world!");
        model.addAttribute("title2", "Hello world222222!");
        return "home2";
    }

    @RequestMapping("/compile")
    public String compile(ModelMap model){
        model.addAttribute("title", "Maven mit exit code " + mavenCompiler.mvn("/Users/vs/release/baasinga/web") + " ausgeführt!");
        return "home";
    }

    @Autowired
    ServletContext servletContext;

    @RequestMapping("/freemarker")
    public String freemarker(ModelMap model){
        //LOG.info(servletContext.getServerInfo());
        String fullPath = servletContext.getRealPath("/WEB-INF/classes");
        templateRenderer.renderClass(fullPath);
        model.addAttribute("title", "Template wurde generiert");
        return "home";
    }

    @RequestMapping("/generate")
    public String generate(ModelMap model){
        //LOG.info(servletContext.getServerInfo());
        String fullPath = servletContext.getRealPath("/WEB-INF/classes");
        applicationGenerator.generateApplication(fullPath);
        model.addAttribute("title", "App wurde generiert");
        return "home";
    }

}
