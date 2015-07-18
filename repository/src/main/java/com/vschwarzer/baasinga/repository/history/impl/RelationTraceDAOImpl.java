package com.vschwarzer.baasinga.repository.history.impl;

import com.vschwarzer.baasinga.domain.model.history.ApplicationTrace;
import com.vschwarzer.baasinga.domain.model.history.RelationTrace;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.history.ApplicationTraceDAO;
import com.vschwarzer.baasinga.repository.history.RelationTraceDAO;

/**
 * Created by Vincent Schwarzer on 18.07.15.
 */
public class RelationTraceDAOImpl extends GenericDAOImpl<RelationTrace> implements RelationTraceDAO {

    /**
     * Version DAO constructor.
     */
    public RelationTraceDAOImpl() {
        super();
        setClazz(RelationTrace.class);
    }
}
