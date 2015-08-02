package com.vschwarzer.baasinga.service.impl;

import com.vschwarzer.baasinga.domain.dto.application.AppDTO;
import com.vschwarzer.baasinga.domain.dto.application.AttributeDTO;
import com.vschwarzer.baasinga.domain.dto.application.ModelDTO;
import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.render.*;
import com.vschwarzer.baasinga.repository.render.*;
import com.vschwarzer.baasinga.service.ApplicationService;
import com.vschwarzer.baasinga.service.common.AbstractService;
import com.vschwarzer.baasinga.service.common.ApplicationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Vincent Schwarzer on 27.07.15.
 */
@Service
@Transactional
public class ApplicationServiceImpl extends AbstractService implements ApplicationService {

    @Autowired
    ApplicationUtil applicationUtil;

    @Autowired
    ApplicationDAO applicationDAO;


    @Override
    public void createApplication(AppDTO appDTO, User user) {
        LOG.info("Creating new application with name " + appDTO.getName() + " for user " + user.getFirstName() + " " + user.getLastName() + "with id=" + user.getId());
        applicationUtil.createApplication(appDTO, user);
    }

    @Override
    public void updateApplication(AppDTO appDTO, User user) {

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

}
