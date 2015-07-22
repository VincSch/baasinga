package com.vschwarzer.baasinga.domain.model.history;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;
import com.vschwarzer.baasinga.domain.model.render.Annotation;
import com.vschwarzer.baasinga.domain.model.render.Import;
import com.vschwarzer.baasinga.domain.model.render.Version;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity class for repositories history
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_repository_trace")
public class RepositoryTrace extends AbstractBaseAuditEntity {

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "applicationId", referencedColumnName = "id")
    private ApplicationTrace application;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modelId", referencedColumnName = "id")
    private ModelTrace model;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ba_repository_annotation_trace",
            joinColumns = @JoinColumn(unique = false, name = "repositoryId"),
            inverseJoinColumns = @JoinColumn(unique = false, name = "annotationId")
    )
    @OrderColumn(name = "id")
    private Set<Annotation> annotations;

    @OneToMany(mappedBy = "repository", fetch = FetchType.EAGER)
    private Set<MethodTrace> methods;

    @OneToMany(mappedBy = "repository", fetch = FetchType.EAGER)
    private Set<AttributeTrace> attributes;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ba_repository_import_trace",
            joinColumns = @JoinColumn(unique = false, name = "repositoryId"),
            inverseJoinColumns = @JoinColumn(unique = false, name = "importId")
    )
    @OrderColumn(name = "id")
    private Set<Import> imports;

    @ManyToOne(fetch = FetchType.EAGER)
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

    public ModelTrace getModel() {
        return model;
    }

    public void setModel(ModelTrace model) {
        this.model = model;
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }

    public Set<MethodTrace> getMethods() {
        return methods;
    }

    public void setMethods(Set<MethodTrace> methods) {
        this.methods = methods;
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

    public com.vschwarzer.baasinga.domain.model.render.Version getVersion() {
        return version;
    }

    public void setVersion(com.vschwarzer.baasinga.domain.model.render.Version version) {
        this.version = version;
    }
}
