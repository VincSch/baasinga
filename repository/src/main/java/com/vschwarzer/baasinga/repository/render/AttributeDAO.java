package com.vschwarzer.baasinga.repository.render;

import com.vschwarzer.baasinga.domain.model.render.Attribute;
import com.vschwarzer.baasinga.repository.GenericDAO;

/**
 * Interface defining repository methods for attributes.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
public interface AttributeDAO extends GenericDAO<Attribute> {

    /**
     * Find an Attributes by its name
     *
     * @param name Attribute name
     * @return Attribute
     */
    public Attribute findByName(String name);
}
