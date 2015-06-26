package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Entity class for methods.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_method")
public class Method extends AbstractBaseAuditEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String parameter;

    @Column(nullable = false)
    private String returnValue;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "repositoryId", referencedColumnName = "id")
    private Repository repository;

    @OneToMany(mappedBy="method", fetch = FetchType.EAGER)
    private List<Annotation> annotations;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "versionId", referencedColumnName = "id")
    private Version version;

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
