package com.vschwarzer.baasinga.domain.model.common;

/**
 * Created by Vincent Schwarzer on 18.07.15.
 */
public enum RelationType {

    OneToMany(1L),
    OneToOne(2L),
    ManyToOne(3L),
    ManyToMany(4L);

    private Long id;

    RelationType(Long id) {
        this.id = id;
    }

    public static RelationType getById(Long id) {
        for (RelationType e : values()) {
            if (e.id.equals(id)) return e;
        }
        return null;
    }

    public Long getId() {
        return id;
    }
}
