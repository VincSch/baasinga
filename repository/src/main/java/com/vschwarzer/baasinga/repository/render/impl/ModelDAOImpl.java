package com.vschwarzer.baasinga.repository.render.impl;

import com.vschwarzer.baasinga.domain.model.render.Model;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.render.ModelDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * repository methods for models.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Repository
@Transactional
public class ModelDAOImpl extends GenericDAOImpl<Model> implements ModelDAO {

    /**
     * Model DAO constructor.
     */
    public ModelDAOImpl() {
        super();
        setClazz(Model.class);
    }

    @Override
    public Model findByName(String name) {
        String queryString = "SELECT mdoel FROM Model model "
                + "WHERE model.name = :name";

        Query query = createQuery(queryString);
        query.setParameter("name", name);
        return (Model) query.getSingleResult();
    }
}
