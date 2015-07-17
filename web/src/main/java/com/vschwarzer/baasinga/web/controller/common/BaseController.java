package com.vschwarzer.baasinga.web.controller.common;

import com.vschwarzer.baasinga.domain.model.authentication.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

/**
 * Created by Vincent Schwarzer on 07.07.15.
 */
@Controller
public class BaseController {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected User getSessionUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String)
            return null;
        else if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user;
        }

        return null;
    }

    protected boolean isAuthenticated() {
        boolean result = false;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            result = true;
        }

        return result;
    }
}
