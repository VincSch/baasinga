package com.vschwarzer.baasinga.service.util;

import com.vschwarzer.baasinga.domain.dto.application.AppDTO;
import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.history.ApplicationTrace;
import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.domain.model.render.Version;
import com.vschwarzer.baasinga.repository.history.ApplicationTraceDAO;
import com.vschwarzer.baasinga.repository.render.ApplicationDAO;
import com.vschwarzer.baasinga.repository.render.VersionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created by Vincent Schwarzer on 02.08.15.
 */
@Component
@Transactional
public class ApplicationUpdateUtil extends BaseUtil {

    @Autowired
    VersionDAO versionDAO;
    @Autowired
    ApplicationDAO applicationDAO;
    @Autowired
    ApplicationTraceDAO applicationTraceDAO;

    public void updateApplication(AppDTO appDTO, User user) {
        Version releaseVersion = releaseNewVersion(appDTO, user);
        Application application = applicationDAO.findByUserAndId(user, Long.valueOf(appDTO.getId()));
        createApplicationTrace(application);
        application.setUpdatedBy(user);
        application.setCloudEnabled(appDTO.getCloudEnabled());
        application.setSecEnabled(appDTO.getSecEnabled());
        application.setName(appDTO.getName());
        application.setDescription(appDTO.getDescription());
        application.setVersion(releaseVersion);
        applicationDAO.update(application);
        LOG.info("Application " + application.getName() + " with id=" + application.getId() + " has been updated!");
        //createModels(appDTO, application, user);
    }

    private void createApplicationTrace(Application application) {
        ApplicationTrace applicationTrace = new ApplicationTrace();
        applicationTrace.setParentId(application.getId());
        applicationTrace.setName(application.getName());
        applicationTrace.setPort(application.getPort());
        applicationTrace.setCloudEnabled(application.isCloudEnabled());
        applicationTrace.setSecEnabled(application.isSecEnabled());
        applicationTrace.setVersion(application.getVersion());
        applicationTrace.setUser(application.getUser());
        applicationTrace.setCreatedBy(application.getUser());
        applicationTrace.setDescription(application.getDescription());
        applicationTraceDAO.create(applicationTrace);
        LOG.info("ApplicationTrace " + applicationTrace.getName() + " with id=" + applicationTrace.getId() + " has been created!");
    }

    private Version releaseNewVersion(AppDTO appDTO, User user) {
        Version oldVersion = versionDAO.findByName(appDTO.getVersion());
        int newVersionNumber = oldVersion.getVersionNumber() + 1;
        Version newVersion = versionDAO.findByVersionNumber(newVersionNumber);
        if (newVersion == null) {
            newVersion = new Version();
            String newVersionName = String.valueOf(newVersionNumber) + ".0";
            newVersion.setVersionNumber(newVersionNumber);
            newVersion.setName(newVersionName);
            newVersion.setCreatedBy(user);
            versionDAO.create(newVersion);
        }
        return newVersion;
    }
}
