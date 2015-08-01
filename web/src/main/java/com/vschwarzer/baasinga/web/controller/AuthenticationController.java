package com.vschwarzer.baasinga.web.controller;

import com.vschwarzer.baasinga.domain.dto.common.RegisterDTO;
import com.vschwarzer.baasinga.service.AuthenticationService;
import com.vschwarzer.baasinga.web.common.Endpoints;
import com.vschwarzer.baasinga.web.controller.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Vincent Schwarzer on 12.07.15.
 */
@Controller
public class AuthenticationController extends BaseController {

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(value = Endpoints.Login)
    public String login(ModelMap model, boolean registerSuccess) {
        if (registerSuccess)
            model.addAttribute("success", "You're registration was successful, please login to continue.");
        model.addAttribute("content", "authentication/login");
        return "index/index";
    }

    @RequestMapping(value = Endpoints.Logout)
    public String logout(ModelMap model) {
        return "redirect:/";
    }

    @RequestMapping(value = Endpoints.Login_Error)
    public String loginError(ModelMap model) {
        model.addAttribute("error", "Invalid username and password!");
        model.addAttribute("content", "authentication/login");
        return "index/index";
    }

    @RequestMapping(value = Endpoints.SignUp)
    public String signUpContent(ModelMap model) {
        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute("register", registerDTO);
        model.addAttribute("content", "authentication/signup");
        return "index/index";
    }

    @RequestMapping(value = Endpoints.SignUp, params = {Endpoints.SignUp_Param_Register}, method = RequestMethod.POST)
    public String signup(ModelMap model, RegisterDTO registerDTO) {
        model.addAttribute("content", "authentication/signup");
        if (authenticationService.isEmailTaken(registerDTO.getEmail())) {
            model.addAttribute("error", "You're email is already in the system!");
            signUpContent(model);
        } else if (!authenticationService.isPasswordMatching(registerDTO.getPassword(), registerDTO.getPasswordConfirm())) {
            model.addAttribute("error", "The passwords you entered do not match!");
            signUpContent(model);
        } else {
            authenticationService.createUser(registerDTO);
            login(model, true);
        }

        return "index/index";
    }
}
