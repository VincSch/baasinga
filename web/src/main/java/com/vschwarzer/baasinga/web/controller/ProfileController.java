package com.vschwarzer.baasinga.web.controller;

import com.vschwarzer.baasinga.domain.dto.common.ChangePasswordDTO;
import com.vschwarzer.baasinga.domain.dto.common.ChangeProfileDTO;
import com.vschwarzer.baasinga.service.AuthenticationService;
import com.vschwarzer.baasinga.web.common.Endpoints;
import com.vschwarzer.baasinga.web.controller.common.BaseController;
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
    AuthenticationService authenticationService;

    @RequestMapping("/profile")
    public String profile(ModelMap model) {
        ChangeProfileDTO changeProfileDTO = new ChangeProfileDTO();
        changeProfileDTO.setEmail(getSessionUser().getUsername());
        changeProfileDTO.setFirstname(getSessionUser().getFirstName());
        changeProfileDTO.setLastname(getSessionUser().getLastName());
        model.addAttribute("pw", new ChangePasswordDTO());
        model.addAttribute("details", changeProfileDTO);
        model.addAttribute("user", getSessionUser());
        model.addAttribute("content", "profile/edit");
        return "index/index";
    }

    @RequestMapping(value = Endpoints.Profile, params = {Endpoints.Profile_Param_ChangePw})
    public String changePassword(ModelMap model, ChangePasswordDTO changePasswordDTO) {
        if (!authenticationService.isPasswordMatching(changePasswordDTO.getNewPw(), changePasswordDTO.getConfirmPw())) {
            model.addAttribute("error", "You're passwords do not macht!");
        } else {
            authenticationService.changePassword(getSessionUser(), changePasswordDTO);
            model.addAttribute("success", "Password successfully updated! ");
        }

        profile(model);
        return "index/index";
    }

    @RequestMapping(value = Endpoints.Profile, params = {Endpoints.Profile_Param_ChangeEmail})
    public String changeEmail(ModelMap model, ChangeProfileDTO changeProfileDTO) {
        if (authenticationService.isEmailTaken(changeProfileDTO.getEmail())) {
            model.addAttribute("error", "Email already in the system!");
        } else {
            authenticationService.changeUserDetails(getSessionUser(), changeProfileDTO);
            model.addAttribute("success", "Email successfully updated!");
        }

        profile(model);
        return "index/index";
    }

    @RequestMapping(value = Endpoints.Profile, params = {Endpoints.Profile_Param_Edit})
    public String changeDetails(ModelMap model, ChangeProfileDTO changeProfileDTO) {
        authenticationService.changeUserDetails(getSessionUser(), changeProfileDTO);
        model.addAttribute("success", "Profile details successfully updated!");
        profile(model);
        return "index/index";
    }
}
