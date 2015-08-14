package com.vschwarzer.baasinga.repository.history.impl;

import com.vschwarzer.baasinga.domain.model.history.ApplicationTrace;
import com.vschwarzer.baasinga.domain.model.history.ApplicationUserTrace;
import com.vschwarzer.baasinga.domain.model.render.ApplicationUser;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.history.ApplicationTraceDAO;
import com.vschwarzer.baasinga.repository.history.ApplicationUserTraceDAO;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Vincent Schwarzer on 13.08.15.
 */
@Repository
@Transactional
public class ApplicationUserTraceDAOImpl extends GenericDAOImpl<ApplicationUserTrace> implements ApplicationUserTraceDAO {
    /**
     * Version DAO constructor.
     */
    public ApplicationUserTraceDAOImpl() {
        super();
        setClazz(ApplicationUserTrace.class);
    }
}
