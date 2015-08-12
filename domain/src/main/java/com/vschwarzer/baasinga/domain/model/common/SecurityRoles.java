package com.vschwarzer.baasinga.domain.model.common;

/**
 * Created by Vincent Schwarzer on 12.08.15.
 */
public enum SecurityRoles {

    NONE(0L),
    ADMIN(1L),
    USER(2L);

    private Long id;

    SecurityRoles(Long id) {
        this.id = id;
    }

    public static SecurityRoles getById(Long id) {
        for (SecurityRoles e : values()) {
            if (e.id.equals(id)) return e;
        }
        return null;
    }

    public Long getId() {
        return id;
    }
}
