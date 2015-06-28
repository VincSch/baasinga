package com.vschwarzer.baasinga.repository.render;

import com.vschwarzer.baasinga.domain.model.render.Repository;
import com.vschwarzer.baasinga.repository.GenericDAO;

/**
 * Interface defining repository methods for roles.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
public interface RepositoryDAO extends GenericDAO<Repository> {

    /**
     * Find an repository by its name
     * @param name repository name
     * @return repository
     */
    public Repository findByName(String name);
}
