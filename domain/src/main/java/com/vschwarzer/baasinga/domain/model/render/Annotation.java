package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;

import javax.persistence.*;

/**
 * Entity class for annotations.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_annotation")
public class Annotation extends AbstractBaseAuditEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    private Type type;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Repository repository;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Method method;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Model model;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Attribute attribute;

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public enum Type {

        METHOD(1L),
        MODEL(2L),
        REPOSITORY(3L),
        ATTRIBUTE(4L);

        private Long id;

        Type(Long id) {
            this.id = id;
        }

        public static Type getById(Long id) {
            for(Type e : values()) {
                if(e.id.equals(id)) return e;
            }
            return null;
        }

        public java.lang.Long getId() {
            return id;
        }
    }
}
