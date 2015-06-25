package com.vschwarzer.baasinga.repository.render;

import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.domain.model.render.Version;
import com.vschwarzer.baasinga.repository.GenericDAO;

/**
 * Interface defining repository methods for versions.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
public interface VersionDAO extends GenericDAO<Version> {

    /**
     * Find an version by its name
     * @param name version name
     * @return version
     */
    public Version findByName(String name);
}
