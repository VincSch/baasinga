package com.vschwarzer.baasinga.domain;

/**
 * Created by Vincent Schwarzer on 24.06.15.
 */

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Superclass for all entities defining unique identifier.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@MappedSuperclass
public abstract class AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = -4678948635896701029L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);

        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        AbstractBaseEntity other = (AbstractBaseEntity) object;

        return !(!this.getId().equals(other.getId()) && (this.getId() == null || !this.id
                .equals(other.id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.getClass().getName() + " [ID=" + id + "]";
    }
}
