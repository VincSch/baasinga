package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;
import com.vschwarzer.baasinga.domain.model.common.RelationType;

import javax.persistence.*;
import java.util.List;

/**
 * Entity class for relations.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_relation")
public class Relation extends AbstractBaseAuditEntity {

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "ownerId")
    private Model owner;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "childId")
    private Model child;

    @Enumerated(EnumType.ORDINAL)
    private RelationType relationType;

    public Model getOwner() {
        return owner;
    }

    public void setOwner(Model owner) {
        this.owner = owner;
    }

    public Model getChild() {
        return child;
    }

    public void setChild(Model child) {
        this.child = child;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }
}
