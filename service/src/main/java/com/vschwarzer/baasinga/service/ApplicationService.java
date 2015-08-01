package com.vschwarzer.baasinga.service;

import com.vschwarzer.baasinga.domain.dto.application.AppDTO;
import com.vschwarzer.baasinga.domain.model.authentication.User;

/**
 * Created by Vincent Schwarzer on 27.07.15.
 */
public interface ApplicationService {

    void createApplication(AppDTO appDTO, User user);

    void updateApplication(AppDTO appDTO, User user);
}
