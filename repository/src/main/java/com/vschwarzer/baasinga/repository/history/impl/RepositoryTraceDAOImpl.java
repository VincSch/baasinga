package com.vschwarzer.baasinga.repository.history.impl;

import com.vschwarzer.baasinga.domain.model.history.RepositoryTrace;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.history.RepositoryTraceDAO;

/**
 * Created by Vincent Schwarzer on 18.07.15.
 */
public class RepositoryTraceDAOImpl extends GenericDAOImpl<RepositoryTrace> implements RepositoryTraceDAO {
    /**
     * Version DAO constructor.
     */
    public RepositoryTraceDAOImpl() {
        super();
        setClazz(RepositoryTrace.class);
    }
}
