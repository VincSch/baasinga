package com.vschwarzer.baasinga.repository.render.impl;

import com.vschwarzer.baasinga.domain.model.render.Repository;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.render.RepositoryDAO;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * repository methods for repositories.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@org.springframework.stereotype.Repository
@Transactional
public class RepositoryDAOImpl extends GenericDAOImpl<Repository> implements RepositoryDAO {

    /**
     * Repository DAO constructor.
     */
    public RepositoryDAOImpl() {
        super();
        setClazz(Repository.class);
    }

    @Override
    public Repository findByName(String name) {
        String queryString = "SELECT repository FROM Repository repository "
                + "WHERE repository.name = :name";

        Query query = createQuery(queryString);
        query.setParameter("name", name);
        return (Repository) query.getSingleResult();
    }

    @Override
    public Repository findByAppAndModel(Long appId, Long modelId) {
        Repository repository = null;
        String queryString = "SELECT repository FROM Repository repository "
                + "WHERE repository.model.id = :modelId "
                + "AND repository.application.id = :appId";

        Query query = createQuery(queryString);
        query.setParameter("modelId", modelId);
        query.setParameter("appId", appId);

        try {
            repository = (Repository) query.getSingleResult();
        } catch (NoResultException nrex) {
            LOG.info("No Repository for appId: " + appId + " and modelId: " + modelId + " found!");
        } finally {
            return repository;
        }
    }
}
