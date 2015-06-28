package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;
import com.vschwarzer.baasinga.domain.model.User;

import javax.persistence.*;
import java.util.List;

/**
 * Entity class for applications.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_application")
public class Application extends AbstractBaseAuditEntity{

    @Column(nullable = false, unique = true)
    private String name;

    @Column(unique = true)
    private int port;

    @Column(nullable = false)
    private boolean cloudEnabled;

    @Column(nullable = false)
    private boolean secEnabled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy="application", fetch = FetchType.EAGER)
    private List<Model> models;

    @OneToMany(mappedBy="application", fetch = FetchType.EAGER)
    private List<Repository> repositories;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "versionId", referencedColumnName = "id")
    private Version version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isCloudEnabled() {
        return cloudEnabled;
    }

    public void setCloudEnabled(boolean cloudEnabled) {
        this.cloudEnabled = cloudEnabled;
    }

    public boolean isSecEnabled() {
        return secEnabled;
    }

    public void setSecEnabled(boolean secEnabled) {
        this.secEnabled = secEnabled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}