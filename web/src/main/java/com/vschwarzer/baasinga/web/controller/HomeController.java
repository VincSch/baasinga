package com.vschwarzer.baasinga.web.controller;

import com.vschwarzer.baasinga.domain.model.User;
import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.repository.authorization.UserDAO;
import com.vschwarzer.baasinga.repository.render.ApplicationDAO;
import com.vschwarzer.baasinga.service.generator.ApplicationGenerator;
import com.vschwarzer.baasinga.service.generator.engine.TemplateRenderer;
import com.vschwarzer.baasinga.service.generator.mvn.MavenCompiler;
import com.vschwarzer.baasinga.web.config.common.DataGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "index";
    }

    @RequestMapping("/list")
    public String list(ModelMap model) {
        model.addAttribute("title", "Hello world!");
        model.addAttribute("title2", "Hello world222222!");
        return "home2";
    }

    @RequestMapping("/compile")
    public String compile(ModelMap model){
        model.addAttribute("title", "Maven mit exit code " + mavenCompiler.cleanInstall("/Users/vs/release/baasinga/web") + " ausgeführt!");
        return "home";
    }

    @RequestMapping("/generate")
    public String generate(ModelMap model){
        User user = userDAO.findByEmail("vs@stroodel.com");
        Application application = applicationDAO.findByUser(user);
        applicationGenerator.generateApplication(application);
        model.addAttribute("title", "App wurde generiert");
        return "home";
    }

    @Autowired
    DataGeneratorUtil dataGeneratorUtil;

    @RequestMapping("/insert")
    public String insert(ModelMap model){
        model.addAttribute("title", "Daten wurden eingespielt.");
        dataGeneratorUtil.addTestApplication();
        return "home";
    }

    @Autowired
    ApplicationDAO applicationDAO;

    @Autowired
    UserDAO userDAO;

    @RequestMapping("/fullgen")
    public String fullgen(ModelMap model){
        model.addAttribute("title", "Full gen");
        User user = userDAO.findByEmail("vs@stroodel.com");
        Application application = applicationDAO.findByUser(user);
        return "home";
    }
}
