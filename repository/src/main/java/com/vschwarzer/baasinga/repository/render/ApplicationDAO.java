package com.vschwarzer.baasinga.repository.render;

import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.repository.GenericDAO;

import java.util.List;

/**
 * Interface defining repository methods for roles.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
public interface ApplicationDAO extends GenericDAO<Application> {

    /**
     * Find an application by its name
     *
     * @param name application name
     * @param user {@link com.vschwarzer.baasinga.domain.model.authentication.User}
     * @return {@link com.vschwarzer.baasinga.domain.model.render.Application}
     */
    public Application findByNameAndUser(String name, User user);

    /**
     * Find an application by a user who created it
     *
     * @param user          user object
     * @param applicationId identifier of the application
     * @return {@link com.vschwarzer.baasinga.domain.model.render.Application}
     */
    public Application findByUserAndId(User user, Long applicationId);

    /**
     * Find all application (highest version) for an user
     *
     * @param user {@link com.vschwarzer.baasinga.domain.model.authentication.User}
     * @return List of {@link com.vschwarzer.baasinga.domain.model.render.Application}
     */
    List<Application> getApplicationsForUser(User user);
}
