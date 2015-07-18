package com.vschwarzer.baasinga.repository.render;

import com.vschwarzer.baasinga.domain.model.render.Import;
import com.vschwarzer.baasinga.repository.GenericDAO;

/**
 * Interface defining repository methods for Imports.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
public interface ImportDAO extends GenericDAO<Import> {

    /**
     * Find an Import by its name
     *
     * @param name Import name
     * @return Import
     */
    public Import findByPackage(String name);
}
