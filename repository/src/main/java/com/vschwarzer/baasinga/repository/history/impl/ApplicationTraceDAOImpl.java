package com.vschwarzer.baasinga.repository.history.impl;

import com.vschwarzer.baasinga.domain.model.history.ApplicationTrace;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.history.ApplicationTraceDAO;

/**
 * Created by Vincent Schwarzer on 18.07.15.
 */
public class ApplicationTraceDAOImpl extends GenericDAOImpl<ApplicationTrace> implements ApplicationTraceDAO {

    /**
     * Version DAO constructor.
     */
    public ApplicationTraceDAOImpl() {
        super();
        setClazz(ApplicationTrace.class);
    }
}
