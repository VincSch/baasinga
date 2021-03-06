package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;
import com.vschwarzer.baasinga.domain.model.common.RelationType;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity class for attributes.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_attribute")
public class Attribute extends AbstractBaseAuditEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private DataType dataType;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AttributeType attributeType;

    @Enumerated(EnumType.ORDINAL)
    private RelationType relationType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ba_attribute_annotation",
            joinColumns = @JoinColumn(unique = false, name = "attributeId"),
            inverseJoinColumns = @JoinColumn(unique = false, name = "annotationId")
    )
    private Set<Annotation> annotations;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "versionId", referencedColumnName = "id")
    private Version version;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "modelId", referencedColumnName = "id")
    private Model model;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "childId", referencedColumnName = "id")
    private Model child;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "repositoryId", referencedColumnName = "id")
    private Repository repository;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public Model getChild() {
        return child;
    }

    public void setChild(Model child) {
        this.child = child;
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }

    public Version getVersion() {
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

    public enum DataType {

        STRING(0L, "String"),
        INTEGER(1L, "int"),
        BOOLEAN(2L, "boolean"),
        DOUBLE(3L, "double"),
        MODEL(4L, "mdoel");

        private Long id;
        private String name;

        DataType(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public static DataType getById(Long id) {
            for (DataType e : values()) {
                if (e.id.equals(id)) return e;
            }
            return null;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public enum AttributeType {

        RELATION(0L),
        NORMAL(1L);

        private Long id;

        AttributeType(Long id) {
            this.id = id;
        }

        public static AttributeType getById(Long id) {
            for (AttributeType e : values()) {
                if (e.id.equals(id)) return e;
            }
            return null;
        }

        public Long getId() {
            return id;
        }
    }
}
