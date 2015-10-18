package com.vschwarzer.baasinga.repository.history.impl;

import com.vschwarzer.baasinga.domain.model.history.AttributeTrace;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.history.AttributeTraceDAO;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Vincent Schwarzer on 18.07.15.
 */
@Repository
@Transactional
public class AttributeTraceDAOImpl extends GenericDAOImpl<AttributeTrace> implements AttributeTraceDAO {
    /**
     * Version DAO constructor.
     */
    public AttributeTraceDAOImpl() {
        super();
        setClazz(AttributeTrace.class);
    }

}
