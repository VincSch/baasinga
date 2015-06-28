package com.vschwarzer.baasinga.repository.render.impl;

import com.vschwarzer.baasinga.domain.model.render.Attribute;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.render.AttributeDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * repository methods for versions.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Repository
@Transactional
public class AttributeDAOImpl extends GenericDAOImpl<Attribute> implements AttributeDAO {

    /**
     * Attribute DAO constructor.
     */
    public AttributeDAOImpl() {
        super();
        setClazz(Attribute.class);
    }

    @Override
    public Attribute findByName(String name) {
        String queryString = "SELECT attribute FROM Attribute attribute "
                + "WHERE attribute.name = :name";

        Query query = createQuery(queryString);
        query.setParameter("name", name);
        return (Attribute) query.getSingleResult();
    }
}
