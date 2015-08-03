package com.vschwarzer.baasinga.domain.model.history;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;
import com.vschwarzer.baasinga.domain.BaseHistoryEntity;
import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.render.Version;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity class for applications history
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_application_trace")
public class ApplicationTrace extends BaseHistoryEntity {

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

    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    private Set<ModelTrace> models;

    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    private Set<RepositoryTrace> repositories;

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

    public Set<ModelTrace> getModels() {
        return models;
    }

    public void setModels(Set<ModelTrace> models) {
        this.models = models;
    }

    public Set<RepositoryTrace> getRepositories() {
        return repositories;
    }

    public void setRepositories(Set<RepositoryTrace> repositories) {
        this.repositories = repositories;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}

