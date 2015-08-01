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
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Vincent Schwarzer on 27.07.15.
 */
@Service
@Transactional
public class ApplicationServiceImpl extends AbstractService implements ApplicationService {

    @Autowired
    ApplicationUtil applicationUtil;


    @Override
    public void createApplication(AppDTO appDTO, User user) {
        LOG.info("Creating new application with name " + appDTO.getName() + " for user " + user.getFirstName() + " " + user.getLastName() + "with id=" + user.getId());
        applicationUtil.createApplication(appDTO, user);
    }

    @Override
    public void updateApplication(AppDTO appDTO, User user) {

    }

}
