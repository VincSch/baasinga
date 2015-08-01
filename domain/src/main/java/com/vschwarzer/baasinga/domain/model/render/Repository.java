package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity class for repositories.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_repository")
public class Repository extends AbstractBaseAuditEntity {

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "applicationId", referencedColumnName = "id")
    private Application application;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modelId", referencedColumnName = "id")
    private Model model;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ba_repository_annotation",
            joinColumns = @JoinColumn(unique = false, name = "repositoryId"),
            inverseJoinColumns = @JoinColumn(unique = false, name = "annotationId")
    )
    private Set<Annotation> annotations;

    @OneToMany(mappedBy = "repository", fetch = FetchType.EAGER)
    private Set<Method> methods;

    @OneToMany(mappedBy = "repository", fetch = FetchType.EAGER)
    private Set<Attribute> attributes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ba_repository_import",
            joinColumns = @JoinColumn(unique = false, name = "repositoryId"),
            inverseJoinColumns = @JoinColumn(unique = false, name = "importId")
    )
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

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }

    public Set<Method> getMethods() {
        return methods;
    }

    public void setMethods(Set<Method> methods) {
        this.methods = methods;
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
