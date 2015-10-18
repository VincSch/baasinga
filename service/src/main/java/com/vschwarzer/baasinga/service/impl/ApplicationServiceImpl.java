package com.vschwarzer.baasinga.service.impl;

import com.vschwarzer.baasinga.domain.dto.application.AppDTO;
import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.history.ApplicationTrace;
import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.domain.model.render.Model;
import com.vschwarzer.baasinga.repository.history.ApplicationTraceDAO;
import com.vschwarzer.baasinga.repository.render.ApplicationDAO;
import com.vschwarzer.baasinga.service.ApplicationService;
import com.vschwarzer.baasinga.service.common.BaseService;
import com.vschwarzer.baasinga.service.util.ApplicationCreateUtil;
import com.vschwarzer.baasinga.service.util.ApplicationUpdateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Vincent Schwarzer on 27.07.15.
 */
@Service
@Transactional
public class ApplicationServiceImpl extends BaseService implements ApplicationService {

    @Autowired
    ApplicationCreateUtil applicationCreateUtil;

    @Autowired
    ApplicationUpdateUtil applicationUpdateUtil;

    @Autowired
    ApplicationDAO applicationDAO;

    @Autowired
    ApplicationTraceDAO applicationTraceDAO;


    @Override
    public void createApplication(AppDTO appDTO, User user) {
        LOG.info("Creating new application with name " + appDTO.getName() + " for user " + user.getFirstName() + " " + user.getLastName() + " with id=" + user.getId());
        applicationCreateUtil.createApplication(appDTO, user);
    }

    @Override
    public void updateApplication(AppDTO appDTO, User user) {
        LOG.info("Updating application with name " + appDTO.getName() + " and id = " + appDTO.getId() + " for user " + user.getFirstName() + " " + user.getLastName() + " with id=" + user.getId());
        applicationUpdateUtil.updateApplication(appDTO, user);
    }

    @Override
    public List<Application> getApplicationsForUser(User user) {
        return applicationDAO.getApplicationsForUser(user);
    }

    @Override
    public Map<String, Integer> getApplicationAndModelCountForUser(User user) {
        List<Application> applications = applicationDAO.getApplicationsForUser(user);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("appCount", applications.size());
        Set<Model> models = new HashSet<>();
        for (Application application : applications) {
            models.addAll(application.getModels());
        }
        resultMap.put("modelCount", models.size());
        return resultMap;
    }

    @Override
    public boolean applicationWithNameAlreadyExists(String name, User user) {
        boolean result = false;
        if (applicationDAO.findByNameAndUser(name, user) != null)
            result = true;

        return result;
    }

    @Override
    public List<ApplicationTrace> getApplicationHistoryByUser(Long applicationId, User user) {
        return applicationTraceDAO.getApplicationHistoryByUser(applicationId, user);
    }

}
