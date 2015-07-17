package com.vschwarzer.baasinga.domain.model.history;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;
import com.vschwarzer.baasinga.domain.model.render.*;
import com.vschwarzer.baasinga.domain.model.render.Version;

import javax.persistence.*;
import java.util.List;

/**
 * Entity class for attributes history
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
//@Entity
//@Table(name = "ba_attribute_trace")
public class AttributeTrace extends AbstractBaseAuditEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Attribute.DataType dataType;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ba_attribute_annotation",
            joinColumns = @JoinColumn(unique = false, name = "attributeId"),
            inverseJoinColumns = @JoinColumn(unique = false, name = "annotationId")
    )
    @OrderColumn(name = "id")
    private List<Annotation> annotations;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "versionId", referencedColumnName = "id")
    private com.vschwarzer.baasinga.domain.model.render.Version version;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "modelId", referencedColumnName = "id")
    private Model model;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "repositoryId", referencedColumnName = "id")
    private Repository repository;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Attribute.DataType getDataType() {
        return dataType;
    }

    public void setDataType(Attribute.DataType dataType) {
        this.dataType = dataType;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    public com.vschwarzer.baasinga.domain.model.render.Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
