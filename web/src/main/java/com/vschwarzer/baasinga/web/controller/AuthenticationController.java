package com.vschwarzer.baasinga.web.controller;

import com.vschwarzer.baasinga.web.controller.common.BaseController;
import com.vschwarzer.baasinga.web.form.application.AppDTO;
import com.vschwarzer.baasinga.web.form.application.AttributeDTO;
import com.vschwarzer.baasinga.web.form.application.ModelDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Vincent Schwarzer on 12.07.15.
 */
@Controller
public class AuthenticationController extends BaseController {

    @RequestMapping("/login")
    public String login(ModelMap model) {
        model.addAttribute("content", "authentication/login");
        return "index/index";
    }

    @RequestMapping("/logout")
    public String logout(ModelMap model) {
        return "redirect:/";
    }

    @RequestMapping("/loginerror")
    public String loginError(ModelMap model) {
        model.addAttribute("error", "Invalid username and password!");
        model.addAttribute("content", "authentication/login");
        return "index/index";

    }

    @RequestMapping("/signup")
    public String signup(ModelMap model) {
        model.addAttribute("content", "authentication/signup");
        return "index/index";
    }
}
