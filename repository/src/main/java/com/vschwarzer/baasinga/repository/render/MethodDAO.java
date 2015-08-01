package com.vschwarzer.baasinga.repository.render;

import com.vschwarzer.baasinga.domain.model.render.Method;
import com.vschwarzer.baasinga.repository.GenericDAO;

/**
 * Interface defining repository methods for Methods.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
public interface MethodDAO extends GenericDAO<Method> {

    /**
     * Find an Method by its name
     *
     * @param name Method name
     * @return Method
     */
    public Method findByName(String name);
}
