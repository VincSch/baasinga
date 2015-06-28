package com.vschwarzer.baasinga.repository.render.impl;

import com.vschwarzer.baasinga.domain.model.render.Annotation;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.render.AnnotationDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * repository methods for Annotations.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Repository
@Transactional
public class AnnotationDAOImpl extends GenericDAOImpl<Annotation> implements AnnotationDAO {

    /**
     * Version DAO constructor.
     */
    public AnnotationDAOImpl() {
        super();
        setClazz(Annotation.class);
    }

    @Override
    public Annotation findByName(String name) {
        String queryString = "SELECT annotation FROM Annotation annotation "
                + "WHERE annotation.name = :name";

        Query query = createQuery(queryString);
        query.setParameter("name", name);
        return (Annotation) query.getSingleResult();
    }
}
