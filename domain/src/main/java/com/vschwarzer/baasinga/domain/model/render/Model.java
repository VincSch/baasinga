package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity class for models.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_model")
public class Model extends AbstractBaseAuditEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicationId", referencedColumnName = "id")
    private Application application;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ba_model_annotation",
            joinColumns = @JoinColumn(name = "modelId"),
            inverseJoinColumns = @JoinColumn(unique = false, name = "annotationId")
    )
    private Set<Annotation> annotations;

    @OneToMany(mappedBy = "model", fetch = FetchType.EAGER)
    private Set<Attribute> attributes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ba_model_import",
            joinColumns = @JoinColumn(unique = false, name = "modelId"),
            inverseJoinColumns = @JoinColumn(unique = false, name = "importId")
    )
    private Set<Import> imports;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "versionId", referencedColumnName = "id")
    private Version version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Set<Import> getImports() {
        return imports;
    }

    public void setImports(Set<Import> imports) {
        this.imports = imports;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
