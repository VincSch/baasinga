package com.vschwarzer.baasinga.repository.history;

import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.history.ApplicationTrace;
import com.vschwarzer.baasinga.repository.GenericDAO;

import java.util.List;

/**
 * Interface defining repository methods for application history.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
public interface ApplicationTraceDAO extends GenericDAO<ApplicationTrace> {

    List<ApplicationTrace> getApplicationHistoryByUser(Long parentId, User user);
}
