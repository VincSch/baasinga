package com.vschwarzer.baasinga.repository.authorization;

import com.vschwarzer.baasinga.domain.model.Role;
import com.vschwarzer.baasinga.repository.GenericDAO;

import java.util.List;

/**
 * Interface defining repository methods for roles.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
public interface RoleDAO extends GenericDAO<Role> {

    /**
     * find a role by its unique name.
     *
     * @param name unique name
     * @return the role entity
     */
    Role findByName(final String name);

    /**
     * find admin role.
     *
     * @return the admin role entity
     */
    Role getAdmin();

    /**
     * Returns all roles without admin role.
     *
     * @return all roles without admin role
     */
    List<Role> findAllNonAdminRoles();
}
