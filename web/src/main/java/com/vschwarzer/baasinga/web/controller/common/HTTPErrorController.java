package com.vschwarzer.baasinga.web.controller.common;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Vincent Schwarzer on 02.08.15.
 */
public class HTTPErrorController extends BaseController {

    @RequestMapping(value = "/404")
    public String error404(ModelMap model) {
        model.addAttribute("error", "Ups an 404 happened!");
        model.addAttribute("content", "index/content");
        return "index/index";
    }

}
