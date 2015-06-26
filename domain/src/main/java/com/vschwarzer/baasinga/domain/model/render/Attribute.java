package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Entity class for attributes.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_attribute")
public class Attribute extends AbstractBaseAuditEntity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private DataType dataType;

    @OneToMany(mappedBy="attribute", fetch = FetchType.EAGER)
    private List<Annotation> annotations;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "versionId", referencedColumnName = "id")
    private Version version;

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

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
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

        STRING(1L),
        INTEGER(2L),
        BOOLEAN(3L),
        DOUBLE(4L);

        private Long id;

        DataType(Long id) {
            this.id = id;
        }

        public static DataType getById(Long id) {
            for(DataType e : values()) {
                if(e.id.equals(id)) return e;
            }
            return null;
        }

        public java.lang.Long getId() {
            return id;
        }
    }
}
