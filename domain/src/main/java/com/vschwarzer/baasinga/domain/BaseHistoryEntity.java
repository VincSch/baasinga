package com.vschwarzer.baasinga.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Superclass for all entities defining history specific values.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@MappedSuperclass
public class BaseHistoryEntity extends AbstractBaseAuditEntity{

    @Column
    protected Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
