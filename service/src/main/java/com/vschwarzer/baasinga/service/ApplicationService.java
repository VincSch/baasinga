package com.vschwarzer.baasinga.service;

import com.vschwarzer.baasinga.domain.dto.application.AppDTO;

/**
 * Created by Vincent Schwarzer on 27.07.15.
 */
public interface ApplicationService {

    void storeApplication(AppDTO appDTO);
}
