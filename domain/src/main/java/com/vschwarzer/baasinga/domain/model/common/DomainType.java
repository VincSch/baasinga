package com.vschwarzer.baasinga.domain.model.common;

/**
 * Created by Vincent Schwarzer on 03.07.15.
 */
public enum DomainType {

    METHOD(1L),
    MODEL(2L),
    REPOSITORY(3L),
    ATTRIBUTE(4L);

    private Long id;

    DomainType(Long id) {
        this.id = id;
    }

    public static DomainType getById(Long id) {
        for (DomainType e : values()) {
            if (e.id.equals(id)) return e;
        }
        return null;
    }

    public java.lang.Long getId() {
        return id;
    }
}