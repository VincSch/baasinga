package com.vschwarzer.baasinga.domain.model.history;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;
import com.vschwarzer.baasinga.domain.BaseHistoryEntity;
import com.vschwarzer.baasinga.domain.model.common.SecurityRoles;
import com.vschwarzer.baasinga.domain.model.render.Annotation;
import com.vschwarzer.baasinga.domain.model.render.Import;
import com.vschwarzer.baasinga.domain.model.render.Version;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity class for models history
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_model_trace")
public class ModelTrace extends BaseHistoryEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicationId", referencedColumnName = "id")
    private ApplicationTrace application;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ba_model_annotation_trace",
            joinColumns = @JoinColumn(unique = false, name = "modelId"),
            inverseJoinColumns = @JoinColumn(unique = false, name = "annotationId")
    )
    private Set<Annotation> annotations;

    @OneToMany(mappedBy = "model", fetch = FetchType.EAGER)
    private Set<AttributeTrace> attributes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ba_model_import_trace",
            joinColumns = @JoinColumn(unique = false, name = "modelId"),
            inverseJoinColumns = @JoinColumn(unique = false, name = "importId")
    )
    private Set<Import> imports;

    @Enumerated(EnumType.ORDINAL)
    private SecurityRoles securityRoles;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "versionId", referencedColumnName = "id")
    private Version version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ApplicationTrace getApplication() {
        return application;
    }

    public void setApplication(ApplicationTrace application) {
        this.application = application;
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }

    public Set<AttributeTrace> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<AttributeTrace> attributes) {
        this.attributes = attributes;
    }

    public Set<Import> getImports() {
        return imports;
    }

    public void setImports(Set<Import> imports) {
        this.imports = imports;
    }

    public SecurityRoles getSecurityRoles() {
        return securityRoles;
    }

    public void setSecurityRoles(SecurityRoles securityRoles) {
        this.securityRoles = securityRoles;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}