package com.vschwarzer.baasinga.web.controller;

import com.vschwarzer.baasinga.web.controller.common.BaseController;
import com.vschwarzer.baasinga.domain.dto.common.ChangePasswordDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Vincent Schwarzer on 15.07.15.
 */
@Controller
public class ProfileController extends BaseController {

    @RequestMapping("/profile/edit")
    public String edit(ModelMap model) {
        model.addAttribute("pw", new ChangePasswordDTO());
        model.addAttribute("user", getSessionUser());
        model.addAttribute("content", "profile/edit");
        return "index/index";
    }

    @RequestMapping("/profile/changepw")
    public String changePassword(ModelMap model) {
        model.addAttribute("user", getSessionUser());
        model.addAttribute("content", "dashboard/content");
        return "index/index";
    }
}
