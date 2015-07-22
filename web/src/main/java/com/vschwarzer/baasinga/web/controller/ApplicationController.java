package com.vschwarzer.baasinga.web.controller;

import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.common.RelationType;
import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.domain.model.render.Relation;
import com.vschwarzer.baasinga.repository.authorization.UserDAO;
import com.vschwarzer.baasinga.repository.render.ApplicationDAO;
import com.vschwarzer.baasinga.repository.render.VersionDAO;
import com.vschwarzer.baasinga.service.generator.ApplicationGenerator;
import com.vschwarzer.baasinga.service.generator.engine.TemplateRenderer;
import com.vschwarzer.baasinga.service.generator.mvn.MavenCompiler;
import com.vschwarzer.baasinga.web.common.Endpoints;
import com.vschwarzer.baasinga.web.controller.common.BaseController;
import com.vschwarzer.baasinga.web.form.application.AppDTO;
import com.vschwarzer.baasinga.web.form.application.AttributeDTO;
import com.vschwarzer.baasinga.web.form.application.ModelDTO;
import com.vschwarzer.baasinga.web.form.application.RelationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vs on 21.05.15.
 */
@Controller
public class ApplicationController extends BaseController {

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

    public String show(ModelMap model) {
//        AppDTO appDTO = (AppDTO) model.get("app");
//        model.addAttribute("allModels", appDTO.getModels());
        model.addAttribute("user", getSessionUser());
        model.addAttribute("versions", versionDAO.findAll());
        model.addAttribute("allRelationTypes", getAsList());
        model.addAttribute("content", "application/content");
        return "index/index";
    }

    @RequestMapping(Endpoints.Application_New)
    public String newApp(ModelMap model) {
        AppDTO app = new AppDTO();
        app.getModels().add(new ModelDTO());
        model.addAttribute("app", app);
        return show(model);
    }

    @RequestMapping(value = Endpoints.Application, params = {Endpoints.Application_Param_Save}, method = RequestMethod.POST)
    public String processForm(final AppDTO app) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null)
            LOG.info("firstname" + user.getFirstName());
        LOG.info(app.getName());
        LOG.info(String.valueOf(app.getPort()));
        LOG.info(app.getVersion());
        LOG.info(String.valueOf(app.getCloudEnabled()));
        LOG.info(String.valueOf(app.getSecEnabled()));

        for (ModelDTO model : app.getModels()) {
            LOG.info(model.getName());
            for (AttributeDTO attribute : model.getAttributes()) {
                LOG.info(attribute.getName());
            }

        }

        return "redirect:" + Endpoints.Dashboard;
    }

    @RequestMapping(value = Endpoints.Application, params = {Endpoints.Application_Param_AddModel})
    public String addModel(final AppDTO app, ModelMap model) {
        ModelDTO modelDTO = new ModelDTO();
        app.getModels().add(modelDTO);
        model.addAttribute("app", app);
        return show(model);
    }

    @RequestMapping(value = Endpoints.Application, params = {Endpoints.Application_Param_SaveModel})
    public String saveModel(final AppDTO app, ModelMap model) {
        model.addAttribute("app", app);
        return show(model);
    }

    @RequestMapping(value = Endpoints.Application, params = {Endpoints.Application_Param_RemoveModel})
    public String removeModel(final AppDTO app, final HttpServletRequest req, ModelMap model) {
        final Integer modelId = Integer.valueOf(req.getParameter("removeModel"));
        app.getModels().remove(modelId.intValue());
        model.addAttribute("app", app);
        return show(model);
    }

    @RequestMapping(value = Endpoints.Application, params = {Endpoints.Application_Param_AddAttribute})
    public String addAttributeToModel(final AppDTO app, final HttpServletRequest req, ModelMap model) {
        final Integer modelId = Integer.valueOf(req.getParameter("addAttribute"));
        app.getModels().get(modelId).getAttributes().add(new AttributeDTO());
        model.addAttribute("app", app);
        return show(model);
    }

    @RequestMapping(value = Endpoints.Application, params = {Endpoints.Application_Param_RemoveAttribute})
    public String removeAttributeFromModel(final AppDTO app, final HttpServletRequest req, ModelMap model) {
        final Integer attributeId = Integer.valueOf(req.getParameter("removeAttribute"));
        final Integer modelId = Integer.valueOf(req.getParameter("modelId"));
        app.getModels().get(modelId.intValue()).getAttributes().remove(attributeId.intValue());
        model.addAttribute("app", app);
        return show(model);
    }

    @RequestMapping(value = Endpoints.Application, params = {Endpoints.Application_Param_AddRelation})
    public String addRelationToModel(final AppDTO app, final HttpServletRequest req, ModelMap model) {
        final Integer modelId = Integer.valueOf(req.getParameter("addRelation"));
        app.getModels().get(modelId).getRelations().add(new RelationDTO());
        model.addAttribute("app", app);
        return show(model);
    }

    @RequestMapping(value = Endpoints.Application, params = {Endpoints.Application_Param_RemoveRelation})
    public String removeRelationFromModel(final AppDTO app, final HttpServletRequest req, ModelMap model) {
        final Integer relationId = Integer.valueOf(req.getParameter("removeRelation"));
        final Integer modelId = Integer.valueOf(req.getParameter("modelId"));
        app.getModels().get(modelId.intValue()).getRelations().remove(relationId.intValue());
        model.addAttribute("app", app);
        return show(model);
    }

    @RequestMapping(value = {Endpoints.Application_Edit}, method = RequestMethod.GET)
    public String editApp(@PathVariable(value = "appId") final String appId, ModelMap model) {
        int appIdAsInt = Integer.valueOf(appId);
        Application app = applicationDAO.findOne(appIdAsInt);
        model.addAttribute("app", app);
        return show(model);
    }

    @RequestMapping(value = {Endpoints.Application_Details}, method = RequestMethod.GET)
    public String details(@PathVariable(value = "appId") final String appId, ModelMap model) {
        int appIdAsInt = Integer.valueOf(appId);
        model.addAttribute("user", getSessionUser());
        model.addAttribute("applications", applicationDAO.findAll());
        model.addAttribute("content", "application/details/content");
        return "index/index";
    }

    private List<RelationType> getAsList() {
        List<RelationType> relationTypes = new ArrayList<>();
        relationTypes.add(RelationType.OneToOne);
        relationTypes.add(RelationType.ManyToOne);

        return relationTypes;
    }
}

