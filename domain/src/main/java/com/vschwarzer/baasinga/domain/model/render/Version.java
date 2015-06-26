package com.vschwarzer.baasinga.domain.model.render;

import com.sun.istack.internal.NotNull;
import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity class for the version.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name="ba_version")
public class Version extends AbstractBaseAuditEntity{

    @Column
    private String description;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
