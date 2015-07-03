package com.vschwarzer.baasinga.repository.render.impl;

import com.vschwarzer.baasinga.domain.model.User;
import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.render.ApplicationDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;

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
    public Application findByName(String name) {
        String queryString = "SELECT application FROM Application application "
                + "WHERE application.name = :name";

        Query query = createQuery(queryString);
        query.setParameter("name", name);
        return (Application) query.getSingleResult();
    }

    @Override
    public Application findByUser(User user) {
        String queryString = "SELECT application FROM Application application "
                + "WHERE application.user = :user";

        Query query = createQuery(queryString);
        query.setParameter("user", user);
        return (Application) query.getSingleResult();
    }


}
