package com.vschwarzer.baasinga.web.controller;

import com.vschwarzer.baasinga.domain.dto.application.AppDTO;
import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.service.ApplicationService;
import com.vschwarzer.baasinga.web.common.Endpoints;
import com.vschwarzer.baasinga.web.controller.common.BaseController;
import com.vschwarzer.baasinga.web.controller.common.RequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Vincent Schwarzer on 18.07.15.
 */
@Controller
public class DashboardController extends BaseController {

    @Autowired
    ApplicationService applicationService;

    @Autowired
    RequestHelper requestHelper;

    @RequestMapping(Endpoints.Dashboard)
    public String show(ModelMap model) {
        User user = getSessionUser();
        Map<String, Integer> statisticMap = applicationService.getApplicationAndModelCountForUser(user);
        model.addAttribute("user", user);

        List<AppDTO> appDTOList = new ArrayList<>();
        for (Application application : applicationService.getApplicationsForUser(user)) {
            appDTOList.add(requestHelper.parseToDTO(application));
        }
        model.addAttribute("applications", appDTOList);
        model.addAttribute("overAllApps", statisticMap.get("appCount"));
        model.addAttribute("overAllModels", statisticMap.get("modelCount"));
        model.addAttribute("content", "dashboard/content");
        return "index/index";
    }
}
