package com.vschwarzer.baasinga.repository.render.impl;

import com.vschwarzer.baasinga.domain.model.render.Method;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.render.MethodDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * repository methods for Methods.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Repository
@Transactional
public class MethodDAOImpl extends GenericDAOImpl<Method> implements MethodDAO {

    /**
     * Version DAO constructor.
     */
    public MethodDAOImpl() {
        super();
        setClazz(Method.class);
    }

    @Override
    public Method findByName(String name) {
        String queryString = "SELECT method FROM Method method "
                + "WHERE method.name = :name";

        Query query = createQuery(queryString);
        query.setParameter("name", name);
        return (Method) query.getSingleResult();
    }
}
