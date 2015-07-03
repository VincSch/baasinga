package com.vschwarzer.baasinga.repository.render.impl;

import com.vschwarzer.baasinga.domain.model.render.Version;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.render.VersionDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * repository methods for versions.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Repository
@Transactional
public class VersionDAOImpl extends GenericDAOImpl<Version> implements VersionDAO {

    /**
     * Version DAO constructor.
     */
    public VersionDAOImpl() {
        super();
        setClazz(Version.class);
    }

    @Override
    public Version findByName(String name) {
        String queryString = "SELECT version FROM Version version "
                + "WHERE version.name = :name";

        Query query = createQuery(queryString);
        query.setParameter("name", name);
        return (Version) query.getSingleResult();
    }
}
