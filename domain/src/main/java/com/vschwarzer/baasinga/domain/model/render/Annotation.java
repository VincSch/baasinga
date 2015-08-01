package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;
import com.vschwarzer.baasinga.domain.model.common.DomainType;
import com.vschwarzer.baasinga.domain.model.common.RelationType;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity class for annotations.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_annotation")
public class Annotation extends AbstractBaseAuditEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = true)
    private String value;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ba_annotation_import",
            joinColumns = @JoinColumn(unique = false, name = "annotationId"),
            inverseJoinColumns = @JoinColumn(unique = false, name = "importId")
    )
    private Set<Import> imports;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private DomainType type;

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DomainType getType() {
        return type;
    }

    public void setType(DomainType type) {
        this.type = type;
    }

    public Set<Import> getImports() {
        return imports;
    }

    public void setImports(Set<Import> imports) {
        this.imports = imports;
    }
}
