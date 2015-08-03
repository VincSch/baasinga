package com.vschwarzer.baasinga.repository.history.impl;

import com.vschwarzer.baasinga.domain.model.history.RepositoryTrace;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.history.RepositoryTraceDAO;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Vincent Schwarzer on 18.07.15.
 */
@Repository
@Transactional
public class RepositoryTraceDAOImpl extends GenericDAOImpl<RepositoryTrace> implements RepositoryTraceDAO {
    /**
     * Version DAO constructor.
     */
    public RepositoryTraceDAOImpl() {
        super();
        setClazz(RepositoryTrace.class);
    }
}
