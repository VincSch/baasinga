package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;

import javax.persistence.Entity;

/**
 * Created by Vincent Schwarzer on 24.06.15.
 */
@Entity
public class Method extends AbstractBaseAuditEntity {

    private String name;
    private String parameter;
    private String returnValue;
}
