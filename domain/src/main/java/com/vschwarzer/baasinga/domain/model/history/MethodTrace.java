package com.vschwarzer.baasinga.domain.model.history;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;
import com.vschwarzer.baasinga.domain.BaseHistoryEntity;
import com.vschwarzer.baasinga.domain.model.render.Annotation;
import com.vschwarzer.baasinga.domain.model.render.Version;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity class for methods history
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_method_trace")
public class MethodTrace extends BaseHistoryEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String parameter;

    @Column(nullable = false)
    private String returnValue;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "repositoryId", referencedColumnName = "id")
    private RepositoryTrace repository;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ba_method_annotation_trace",
            joinColumns = @JoinColumn(unique = false, name = "methodId"),
            inverseJoinColumns = @JoinColumn(unique = false, name = "annotationId")
    )
    @OrderColumn(name = "id")
    private Set<Annotation> annotations;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "versionId", referencedColumnName = "id")
    private Version version;

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }

    public RepositoryTrace getRepository() {
        return repository;
    }

    public void setRepository(RepositoryTrace repository) {
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

