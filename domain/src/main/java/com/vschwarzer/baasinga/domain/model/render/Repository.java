package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Entity class for repositories.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_repository")
public class Repository extends AbstractBaseAuditEntity{

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private Application application;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Annotation> annotations;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Method> methods;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Attribute> attributes;

    //@JoinTable(name = "ba_repositoryimport")
    @OneToMany(fetch = FetchType.EAGER)
    private List<Import> imports;

    @ManyToOne(fetch = FetchType.EAGER)
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

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Import> getImports() {
        return imports;
    }

    public void setImports(List<Import> imports) {
        this.imports = imports;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
