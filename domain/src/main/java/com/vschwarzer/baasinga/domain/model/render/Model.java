package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Entity class for models.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_model")
public class Model extends AbstractBaseAuditEntity{

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "applicationId", referencedColumnName = "id")
    private Application application;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ba_model_annotation",
            joinColumns = @JoinColumn(unique = false, name = "modelId"),
            inverseJoinColumns = @JoinColumn(unique = false, name = "annotationId")
    )
    @OrderColumn(name = "id")
    private List<Annotation> annotations;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Relation> relations;

    @OneToMany(mappedBy="model", fetch = FetchType.EAGER)
    private List<Attribute> attributes;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ba_model_import",
            joinColumns = @JoinColumn(unique = false, name = "modelId"),
            inverseJoinColumns = @JoinColumn(unique = false, name = "importId")
    )
    @OrderColumn(name = "id")
    private List<Import> imports;

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

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
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
