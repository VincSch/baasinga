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

    @Column(nullable = true)
    private String value;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Type type;


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
