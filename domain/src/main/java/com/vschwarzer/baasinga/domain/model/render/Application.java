package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;
import com.vschwarzer.baasinga.domain.model.User;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Vincent Schwarzer on 24.06.15.
 */

@Entity
public class Application extends AbstractBaseAuditEntity{

    private String name;

    private int port;

    private boolean cloudEnabled;

    private boolean secEnabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Model> models;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Repository> repositories;
}
