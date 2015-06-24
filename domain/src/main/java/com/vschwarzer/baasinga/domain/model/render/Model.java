package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Created by Vincent Schwarzer on 24.06.15.
 */
@Entity
public class Model extends AbstractBaseAuditEntity{

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;
}
