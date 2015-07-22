package com.vschwarzer.baasinga.domain.model.history;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;
import com.vschwarzer.baasinga.domain.model.common.RelationType;
import com.vschwarzer.baasinga.domain.model.render.Version;

import javax.persistence.*;

/**
 * Entity class for relations history
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_relation_trace")
public class RelationTrace extends AbstractBaseAuditEntity {

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "ownerId")
    private ModelTrace owner;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "childId")
    private ModelTrace child;

    @Enumerated(EnumType.ORDINAL)
    private RelationType relationType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "versionId", referencedColumnName = "id")
    private Version version;

    public ModelTrace getOwner() {
        return owner;
    }

    public void setOwner(ModelTrace owner) {
        this.owner = owner;
    }

    public ModelTrace getChild() {
        return child;
    }

    public void setChild(ModelTrace child) {
        this.child = child;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
