package com.vschwarzer.baasinga.repository.history.impl;

import com.vschwarzer.baasinga.domain.model.history.ModelTrace;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.history.ModelTraceDAO;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Vincent Schwarzer on 18.07.15.
 */
@Repository
@Transactional
public class ModelTraceDAOImpl extends GenericDAOImpl<ModelTrace> implements ModelTraceDAO {
    /**
     * Version DAO constructor.
     */
    public ModelTraceDAOImpl() {
        super();
        setClazz(ModelTrace.class);
    }
}
