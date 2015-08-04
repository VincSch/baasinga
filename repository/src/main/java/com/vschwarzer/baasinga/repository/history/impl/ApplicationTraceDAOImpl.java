package com.vschwarzer.baasinga.repository.history.impl;

import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.history.ApplicationTrace;
import com.vschwarzer.baasinga.domain.model.render.Import;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.history.ApplicationTraceDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Vincent Schwarzer on 18.07.15.
 */
@Repository
@Transactional
public class ApplicationTraceDAOImpl extends GenericDAOImpl<ApplicationTrace> implements ApplicationTraceDAO {

    /**
     * Version DAO constructor.
     */
    public ApplicationTraceDAOImpl() {
        super();
        setClazz(ApplicationTrace.class);
    }

    @Override
    public List<ApplicationTrace> getApplicationHistoryByUser(Long parentId, User user) {
        String queryString = "SELECT appTrace FROM ApplicationTrace appTrace "
                + "WHERE appTrace.parentId = :parentId "
                + "ORDER BY appTrace.version.versionNumber ASC";

        Query query = createQuery(queryString);
        query.setParameter("parentId", parentId);
        return query.getResultList();
    }
}
