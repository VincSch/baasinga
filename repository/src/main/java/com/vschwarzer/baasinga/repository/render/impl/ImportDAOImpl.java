package com.vschwarzer.baasinga.repository.render.impl;

import com.vschwarzer.baasinga.domain.model.render.Import;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.render.ImportDAO;
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
public class ImportDAOImpl extends GenericDAOImpl<Import> implements ImportDAO {

    /**
     * Version DAO constructor.
     */
    public ImportDAOImpl() {
        super();
        setClazz(Import.class);
    }

    @Override
    public Import findByPackage(String name) {
        String queryString = "SELECT import FROM Import import "
                + "WHERE import.package = :name";

        Query query = createQuery(queryString);
        query.setParameter("name", name);
        return (Import) query.getSingleResult();
    }
}
