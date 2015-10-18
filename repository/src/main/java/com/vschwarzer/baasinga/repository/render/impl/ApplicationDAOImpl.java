package com.vschwarzer.baasinga.repository.render.impl;

import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.render.ApplicationDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * repository methods for applications.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Repository
@Transactional
public class ApplicationDAOImpl extends GenericDAOImpl<Application> implements ApplicationDAO {

    /**
     * Application DAO constructor.
     */
    public ApplicationDAOImpl() {
        super();
        setClazz(Application.class);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public Application findByNameAndUser(String name, User user) {
        Application application = null;
        String queryString = "SELECT application FROM Application application "
                + "WHERE application.name = :name "
                + "AND application.user.id = :userId";

        Query query = createQuery(queryString);
        query.setParameter("name", name);
        query.setParameter("userId", user.getId());
        try {
            application = (Application) query.getSingleResult();
        } catch (NoResultException nrex) {
            LOG.info("No application for name: " + name + " and user " + user.getUsername() + " found!");
        } finally {
            return application;
        }
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public Application findByUserAndId(User user, Long applicationId) {
        String queryString = "SELECT application FROM Application application "
                + "WHERE application.user.id = :userId "
                + "AND application.id = :applicationId";

        Query query = createQuery(queryString);
        query.setParameter("userId", user.getId());
        query.setParameter("applicationId", applicationId);
        return (Application) query.getSingleResult();
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public List<Application> getApplicationsForUser(User user) {
        String queryString = "SELECT application FROM Application application "
                + "WHERE application.user.id = :userId";

        Query query = createQuery(queryString);
        query.setParameter("userId", user.getId());
        return query.getResultList();
    }


}
