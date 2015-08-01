package com.vschwarzer.baasinga.repository.authorization.impl;

import com.vschwarzer.baasinga.domain.model.authentication.UserRole;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.authorization.UserRoleDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * repository methods for user roles.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Repository
@Transactional
public class UserRoleDAOImpl extends GenericDAOImpl<UserRole> implements
        UserRoleDAO {

    /**
     * User Role DAO constructor.
     */
    public UserRoleDAOImpl() {
        super();
        setClazz(UserRole.class);
    }

    @Override
    public List<UserRole> findByUserId(final Long id) {
        String queryString = "SELECT DISTINCT(userRole) FROM UserRole userRole "
                + "LEFT JOIN FETCH userRole.user user "
                + "LEFT JOIN FETCH userRole.role role " + "WHERE user.id = ?1";

        Query query = createQuery(queryString);
        query.setParameter(1, id);
        return query.getResultList();
    }

    @Override
    public List<UserRole> findByUserAndRoleId(final Long userId,
                                              final Long roleId) {
        String queryString = "SELECT DISTINCT(userRole) FROM UserRole userRole "
                + "LEFT JOIN FETCH userRole.user user "
                + "LEFT JOIN FETCH userRole.role role "
                + "WHERE user.id = ?1 AND role.id = ?2";

        Query query = createQuery(queryString);
        query.setParameter(1, userId);
        query.setParameter(2, roleId);
        return query.getResultList();
    }

}
