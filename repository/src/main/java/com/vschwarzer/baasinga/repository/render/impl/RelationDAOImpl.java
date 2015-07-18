package com.vschwarzer.baasinga.repository.render.impl;

import com.vschwarzer.baasinga.domain.model.render.Model;
import com.vschwarzer.baasinga.domain.model.render.Relation;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.render.ModelDAO;
import com.vschwarzer.baasinga.repository.render.RelationDAO;
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
public class RelationDAOImpl extends GenericDAOImpl<Relation> implements RelationDAO {

    /**
     * Model DAO constructor.
     */
    public RelationDAOImpl() {
        super();
        setClazz(Relation.class);
    }
}
