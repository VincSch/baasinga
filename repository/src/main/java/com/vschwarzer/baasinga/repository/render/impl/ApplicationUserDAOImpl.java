package com.vschwarzer.baasinga.repository.render.impl;

import com.vschwarzer.baasinga.domain.model.render.ApplicationUser;
import com.vschwarzer.baasinga.repository.GenericDAOImpl;
import com.vschwarzer.baasinga.repository.render.ApplicationUserDAO;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Vincent Schwarzer on 12.08.15.
 */
@Repository
@Transactional
public class ApplicationUserDAOImpl extends GenericDAOImpl<ApplicationUser> implements ApplicationUserDAO {
    /**
     * Attribute DAO constructor.
     */
    public ApplicationUserDAOImpl() {
        super();
        setClazz(ApplicationUser.class);
    }
}
