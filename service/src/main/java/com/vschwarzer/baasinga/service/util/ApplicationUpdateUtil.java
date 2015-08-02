package com.vschwarzer.baasinga.service.util;

import com.vschwarzer.baasinga.domain.dto.application.AppDTO;
import com.vschwarzer.baasinga.domain.model.authentication.User;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created by Vincent Schwarzer on 02.08.15.
 */
@Component
@Transactional
public class ApplicationUpdateUtil extends BaseUtil {

    public void updateApplication(AppDTO appDTO, User user) {

    }
}
