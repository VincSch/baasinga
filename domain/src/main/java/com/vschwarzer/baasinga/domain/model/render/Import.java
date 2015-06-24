package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;

import javax.persistence.Entity;

/**
 * Created by Vincent Schwarzer on 24.06.15.
 */
@Entity
public class Import extends AbstractBaseAuditEntity{

    private String packageName;
    //TODO
    private String type;
}
