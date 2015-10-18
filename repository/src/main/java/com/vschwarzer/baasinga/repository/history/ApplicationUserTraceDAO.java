package com.vschwarzer.baasinga.repository.history;

import com.vschwarzer.baasinga.domain.model.history.ApplicationTrace;
import com.vschwarzer.baasinga.domain.model.history.ApplicationUserTrace;
import com.vschwarzer.baasinga.domain.model.render.ApplicationUser;
import com.vschwarzer.baasinga.repository.GenericDAO;

/**
 * Interface defining repository methods for application user history.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
public interface ApplicationUserTraceDAO extends GenericDAO<ApplicationUserTrace> {
}
