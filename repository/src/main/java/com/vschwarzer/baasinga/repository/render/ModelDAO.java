package com.vschwarzer.baasinga.repository.render;

import com.vschwarzer.baasinga.domain.model.render.Model;
import com.vschwarzer.baasinga.repository.GenericDAO;

/**
 * Interface defining repository methods for models.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
public interface ModelDAO extends GenericDAO<Model> {

    /**
     * Find an mdoel by its name
     *
     * @param name model name
     * @return model
     */
    public Model findByName(String name);
}
