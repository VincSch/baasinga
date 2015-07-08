package com.vschwarzer.baasinga.web.controller;

import com.vschwarzer.baasinga.repository.authorization.UserDAO;
import com.vschwarzer.baasinga.repository.render.ApplicationDAO;
import com.vschwarzer.baasinga.repository.render.VersionDAO;
import com.vschwarzer.baasinga.service.generator.ApplicationGenerator;
import com.vschwarzer.baasinga.service.generator.engine.TemplateRenderer;
import com.vschwarzer.baasinga.service.generator.mvn.MavenCompiler;
import com.vschwarzer.baasinga.web.controller.common.BaseController;
import com.vschwarzer.baasinga.web.form.application.AppDTO;
import com.vschwarzer.baasinga.web.form.application.AttributeDTO;
import com.vschwarzer.baasinga.web.form.application.ModelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by vs on 21.05.15.
 */
@Controller
@RequestMapping("/build")
public class AppController extends BaseController {

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
    VersionDAO versionDAO;

    @RequestMapping("/application")
    public String show(ModelMap model) {
        AppDTO app = new AppDTO();
        app.getModels().add(new ModelDTO());
        app.getModels().get(0).getAttributes().add(new AttributeDTO());
        model.addAttribute("app", app);
        model.addAttribute("versions", versionDAO.findAll());
        model.addAttribute("content", "application/content");
        return "index/index";
    }

    @RequestMapping(value = "/application", params = {"save"}, method = RequestMethod.POST)
    public String processForm(final AppDTO app) {
        LOG.info(app.getName());
        LOG.info(String.valueOf(app.getPort()));
        LOG.info(app.getVersion());
        LOG.info(String.valueOf(app.getCloudEnabled()));
        LOG.info(String.valueOf(app.getSecEnabled()));

        for (ModelDTO model : app.getModels()) {
            LOG.info(model.getName());
            for(AttributeDTO attribute : model.getAttributes()){
                LOG.info(attribute.getName());
            }

        }

        return "redirect:/";
    }

    @RequestMapping(value = "/application", params = {"addModel"})
    public String addModel(final AppDTO app, ModelMap model) {
        ModelDTO modelDTO = new ModelDTO();
        modelDTO.getAttributes().add(new AttributeDTO());
        app.getModels().add(modelDTO);
        model.addAttribute("app", app);
        model.addAttribute("versions", versionDAO.findAll());
        model.addAttribute("content", "application/content");
        return "index/index";
    }

    @RequestMapping(value = "/application", params = {"removeModel"})
    public String removeModel(final AppDTO app, final HttpServletRequest req, ModelMap model) {
        final Integer modelId = Integer.valueOf(req.getParameter("removeModel"));
        app.getModels().remove(modelId.intValue());
        model.addAttribute("app", app);
        model.addAttribute("versions", versionDAO.findAll());
        model.addAttribute("content", "application/content");
        return "index/index";
    }

    @RequestMapping(value = "/application", params = {"addAttribute"})
    public String addAttributeToModel(final AppDTO app, final HttpServletRequest req, ModelMap model) {
        final Integer modelId = Integer.valueOf(req.getParameter("addAttribute"));
        app.getModels().get(modelId).getAttributes().add(new AttributeDTO());
        model.addAttribute("app", app);
        model.addAttribute("versions", versionDAO.findAll());
        model.addAttribute("content", "application/content");
        return "index/index";
    }

    @RequestMapping(value = "/application", params = {"removeAttribute"})
    public String removeAttributeFromModel(final AppDTO app, final HttpServletRequest req, ModelMap model) {
        final Integer attributeId = Integer.valueOf(req.getParameter("removeAttribute"));
        final Integer modelId = Integer.valueOf(req.getParameter("modelId"));
        app.getModels().get(modelId.intValue()).getAttributes().remove(attributeId.intValue());
        model.addAttribute("app", app);
        model.addAttribute("versions", versionDAO.findAll());
        model.addAttribute("content", "application/content");
        return "index/index";
    }
}
