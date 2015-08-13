package com.vschwarzer.baasinga.web.controller;

import com.vschwarzer.baasinga.domain.dto.application.*;
import com.vschwarzer.baasinga.domain.dto.common.ApplicationStatisticDTO;
import com.vschwarzer.baasinga.domain.model.history.ApplicationTrace;
import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.repository.authorization.UserDAO;
import com.vschwarzer.baasinga.repository.history.ApplicationTraceDAO;
import com.vschwarzer.baasinga.repository.render.ApplicationDAO;
import com.vschwarzer.baasinga.repository.render.VersionDAO;
import com.vschwarzer.baasinga.service.ApplicationService;
import com.vschwarzer.baasinga.service.generator.ApplicationGenerator;
import com.vschwarzer.baasinga.service.generator.engine.TemplateRenderer;
import com.vschwarzer.baasinga.service.generator.mvn.MavenCompiler;
import com.vschwarzer.baasinga.web.common.Endpoints;
import com.vschwarzer.baasinga.web.controller.common.BaseController;
import com.vschwarzer.baasinga.web.controller.common.RequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
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
    RequestHelper requestHelper;
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
    ApplicationTraceDAO applicationTraceDAO;
    @Autowired
    VersionDAO versionDAO;
    @Autowired
    ApplicationService applicationService;
    @Autowired
    DashboardController dashboardController;

    public String show(ModelMap model) {
        model.addAttribute("user", getSessionUser());
        model.addAttribute("allRelationTypes", requestHelper.relationTypeList());
        model.addAttribute("allSecurityRoles", requestHelper.securityRolesList());
        model.addAttribute("content", "application/content");
        return "index/index";
    }

    @RequestMapping(Endpoints.Application_New)
    public String newApp(ModelMap model) {
        AppDTO app = new AppDTO();
        app.setVersion("1.0");
        //app.getModels().add(new ModelDTO());
        app.getApplicationUsers().add(new ApplicationUserDTO());
        model.addAttribute("app", app);
        return show(model);
    }

    @RequestMapping(value = Endpoints.Application, params = {Endpoints.Application_Param_Save}, method = RequestMethod.POST)
    public String processForm(ModelMap model, final AppDTO app) {
        if (app.getId() != null && !app.getId().isEmpty()) {
            applicationService.updateApplication(app, getSessionUser());
            model.addAttribute("success", "Successfuly updated application with name " + app.getName());
        } else {
            if (!applicationService.applicationWithNameAlreadyExists(app.getName(), getSessionUser())) {
                applicationService.createApplication(app, getSessionUser());
                model.addAttribute("success", "Successfuly created application with name " + app.getName());
            } else {
                model.addAttribute("error", "An application with name " + app.getName() + " already exists!");
            }
        }
        model.addAttribute("app", app);
        show(model);
        return "index/index";
    }

    @RequestMapping(value = Endpoints.Application, params = {Endpoints.Application_Param_AddUser})
    public String addAPIUser(final AppDTO app, ModelMap model) {
        ApplicationUserDTO applicationUserDTO = new ApplicationUserDTO();
        app.getApplicationUsers().add(applicationUserDTO);
        model.addAttribute("app", app);
        return show(model);
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
        final String reqString = req.getParameter("removeAttribute");
        int modelId = requestHelper.getModelIndex(reqString);
        int attribtueId = requestHelper.getAttributeOrRelationIndex(reqString);
        app.getModels().get(modelId).getAttributes().remove(attribtueId);
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
        final String reqString = req.getParameter("removeRelation");
        int modelId = requestHelper.getModelIndex(reqString);
        int relationId = requestHelper.getAttributeOrRelationIndex(reqString);
        app.getModels().get(modelId).getRelations().remove(relationId);
        model.addAttribute("app", app);
        return show(model);
    }

    @RequestMapping(value = {Endpoints.Application_Edit}, method = RequestMethod.GET)
    public String editApp(@PathVariable(value = "appId") final String appId, ModelMap model) {
        int appIdAsInt = Integer.valueOf(appId);
        Application app = applicationDAO.findOne(appIdAsInt);
        model.addAttribute("app", requestHelper.parseToDTO(app));
        return show(model);
    }

    @RequestMapping(value = {Endpoints.Application_Details}, method = RequestMethod.GET)
    public String details(@PathVariable(value = "appId") final String appId, ModelMap model) {
        long appIdAsLong = Long.valueOf(appId);
        model.addAttribute("user", getSessionUser());
        Application application = applicationDAO.findByUserAndId(getSessionUser(), appIdAsLong);

        List<ApplicationTrace> applicationTraces = applicationService.getApplicationHistoryByUser(appIdAsLong, getSessionUser());
        if (applicationTraces == null)
            applicationTraces = new ArrayList<>();

        List<AppDTO> appDTOs = requestHelper.parseToDTO(applicationTraces);
        AppDTO currentApp = requestHelper.parseToDTO(application);

        if (applicationTraces.isEmpty() || applicationTraces == null) {
            ApplicationStatisticDTO applicationStatisticDTO = new ApplicationStatisticDTO();
            applicationStatisticDTO.setModelChange("not available");
        } else {
            currentApp.setApplicationStatistic(requestHelper.createHistoryStatistic(application, applicationTraces.get(applicationTraces.size() - 1)));
        }
        model.addAttribute("originalApp", currentApp);
        model.addAttribute("applications", appDTOs);
        model.addAttribute("content", "application/details/content");
        return "index/index";
    }

    @RequestMapping(value = {Endpoints.Application_Details_Show_Original}, method = RequestMethod.GET)
    public String originalDetail(@PathVariable(value = "appId") final String appId,
                                 @PathVariable(value = "originalId") final String originalId, ModelMap model) {

        long appIdAsLong = Long.valueOf(originalId);
        Application application = applicationDAO.findByUserAndId(getSessionUser(), appIdAsLong);
        model.addAttribute("detailApp", requestHelper.parseToDTO(application));
        details(appId, model);
        return "index/index";
    }

    @RequestMapping(value = {Endpoints.Application_Details_Show_History}, method = RequestMethod.GET)
    public String historyDetail(@PathVariable(value = "appId") final String appId,
                                @PathVariable(value = "historyId") final String historyId, ModelMap model) {
        long appIdAsLong = Long.valueOf(historyId);
        ApplicationTrace application = applicationTraceDAO.findByUserAndId(getSessionUser(), appIdAsLong);
        model.addAttribute("detailApp", requestHelper.parseToDTO(application));
        details(appId, model);
        return "index/index";
    }
}

