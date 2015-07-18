package com.vschwarzer.baasinga.web.controller;

import com.vschwarzer.baasinga.repository.render.ApplicationDAO;
import com.vschwarzer.baasinga.web.controller.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Vincent Schwarzer on 18.07.15.
 */
@Controller
public class DashboardController extends BaseController {

    @Autowired
    ApplicationDAO applicationDAO;

    @RequestMapping("/dashboard")
    public String show(ModelMap model) {
        model.addAttribute("user", getSessionUser());
        model.addAttribute("applications", applicationDAO.findAll());
        model.addAttribute("content", "dashboard/content");
        return "index/index";
    }
}
