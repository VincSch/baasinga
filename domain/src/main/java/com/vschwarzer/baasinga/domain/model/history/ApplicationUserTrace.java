package com.vschwarzer.baasinga.domain.model.history;

import com.vschwarzer.baasinga.domain.BaseHistoryEntity;
import com.vschwarzer.baasinga.domain.model.common.SecurityRoles;
import com.vschwarzer.baasinga.domain.model.render.*;
import com.vschwarzer.baasinga.domain.model.render.Version;

import javax.persistence.*;

/**
 * Created by Vincent Schwarzer on 13.08.15.
 */
@Entity
@Table(name = "ba_applicationuser_trace")
public class ApplicationUserTrace extends BaseHistoryEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private SecurityRoles securityRoles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicationId", referencedColumnName = "id")
    private ApplicationTrace application;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "versionId", referencedColumnName = "id")
    private Version version;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ApplicationTrace getApplication() {
        return application;
    }

    public void setApplication(ApplicationTrace application) {
        this.application = application;
    }

    public SecurityRoles getSecurityRoles() {
        return securityRoles;
    }

    public void setSecurityRoles(SecurityRoles securityRoles) {
        this.securityRoles = securityRoles;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
