package com.vschwarzer.baasinga.repository.authorization;

import com.vschwarzer.baasinga.domain.model.authentication.UserRole;
import com.vschwarzer.baasinga.repository.GenericDAO;

import java.util.List;

/**
 * Defining repository access methods for user roles
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
public interface UserRoleDAO extends GenericDAO<UserRole> {

    /**
     * find a user role mapping by a user id.
     *
     * @param id unique user id
     * @return list of UserRole objects
     */
    List<UserRole> findByUserId(final Long id);

    /**
     * find a user role mapping by user and role id.
     *
     * @param userId   unique user id
     * @param recipeId unique role id
     * @return list of UserRole object
     */
    List<UserRole> findByUserAndRoleId(final Long userId, final Long recipeId);
}
