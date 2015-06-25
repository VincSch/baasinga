package com.vschwarzer.baasinga.repository.render;

import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.repository.GenericDAO;

/**
 * Interface defining repository methods for roles.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
public interface ApplicationDAO extends GenericDAO<Application> {

    /**
     * Find an application by its name
     * @param name application name
     * @return application
     */
    public Application findByName(String name);
}
