package com.vschwarzer.baasinga.repository.render;

import com.vschwarzer.baasinga.domain.model.render.Annotation;
import com.vschwarzer.baasinga.domain.model.render.Attribute;
import com.vschwarzer.baasinga.repository.GenericDAO;

/**
 * Interface defining repository methods for Annotations.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
public interface AnnotationDAO extends GenericDAO<Annotation> {

    /**
     * Find an Annotation by its name
     *
     * @param name Annotation name
     * @return Annotation
     */
    public Annotation findByName(String name);
}
