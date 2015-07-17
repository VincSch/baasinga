package com.vschwarzer.baasinga.web.controller;

import com.vschwarzer.baasinga.repository.render.ApplicationDAO;
import com.vschwarzer.baasinga.web.controller.common.BaseController;
import com.vschwarzer.baasinga.web.form.application.AppDTO;
import com.vschwarzer.baasinga.web.form.application.AttributeDTO;
import com.vschwarzer.baasinga.web.form.application.ModelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Vincent Schwarzer on 15.07.15.
 */
@Controller
public class ProfileController extends BaseController {

    @Autowired
    ApplicationDAO applicationDAO;

    @RequestMapping("/profile")
    public String show(ModelMap model) {
        model.addAttribute("user", getSessionUser());
        model.addAttribute("applications", applicationDAO.findAll());
        model.addAttribute("content", "profile/content");
        return "index/index";
    }

    @RequestMapping("/profile/edit")
    public String edit(ModelMap model) {
        model.addAttribute("user", getSessionUser());
        model.addAttribute("content", "profile/edit");
        return "index/index";
    }
}
