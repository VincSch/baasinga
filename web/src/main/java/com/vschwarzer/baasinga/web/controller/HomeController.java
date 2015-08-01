package com.vschwarzer.baasinga.web.controller;

import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.repository.authorization.UserDAO;
import com.vschwarzer.baasinga.repository.render.ApplicationDAO;
import com.vschwarzer.baasinga.service.generator.ApplicationGenerator;
import com.vschwarzer.baasinga.service.generator.engine.TemplateRenderer;
import com.vschwarzer.baasinga.service.generator.mvn.MavenCompiler;
import com.vschwarzer.baasinga.web.config.common.DataGeneratorUtil;
import com.vschwarzer.baasinga.web.controller.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by vs on 21.05.15.
 */
@Controller
public class HomeController extends BaseController {

    @Autowired
    TemplateRenderer templateRenderer;
    @Autowired
    MavenCompiler mavenCompiler;
    @Autowired
    ApplicationGenerator applicationGenerator;
    @Autowired
    UserDAO userDAO;
    @Autowired
    ApplicationDAO applicationDAO;
    @Autowired
    DataGeneratorUtil dataGeneratorUtil;

    @RequestMapping("/")
    public String showHome(ModelMap model) {
        model.addAttribute("user", getSessionUser());
        model.addAttribute("title", "Prototyp Maven CLI Tests");
        model.addAttribute("content", "index/content");
        return "index/index";
    }


    @RequestMapping("/generate")
    public String generate(ModelMap model) {
        User user = userDAO.findByEmail("vs@stroodel.com");
        Application application = applicationDAO.findByUser(user);
        applicationGenerator.generateApplication(application);
        model.addAttribute("title", "App wurde generiert");
        return "/index/index";
    }

    @RequestMapping("/insert")
    public String insertTestData(ModelMap model) {
        dataGeneratorUtil.insertCommonData();
        model.addAttribute("title", "Prototyp Maven CLI Tests");
        model.addAttribute("content", "index/content");
        return "index/index";
    }
}
