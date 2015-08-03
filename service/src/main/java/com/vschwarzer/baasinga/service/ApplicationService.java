package com.vschwarzer.baasinga.service;

import com.vschwarzer.baasinga.domain.dto.application.AppDTO;
import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.history.ApplicationTrace;
import com.vschwarzer.baasinga.domain.model.render.Application;

import java.util.List;
import java.util.Map;

/**
 * Created by Vincent Schwarzer on 27.07.15.
 */
public interface ApplicationService {

    void createApplication(AppDTO appDTO, User user);

    void updateApplication(AppDTO appDTO, User user);

    List<Application> getApplicationsForUser(User user);

    Map<String, Integer> getApplicationAndModelCountForUser(User user);

    boolean applicationWithNameAlreadyExists(String name, User user);

    List<ApplicationTrace> getApplicationHistoryByUser(Long applicationId, User user);
}
