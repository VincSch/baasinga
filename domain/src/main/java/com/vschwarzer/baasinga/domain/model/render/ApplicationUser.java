package com.vschwarzer.baasinga.domain.model.render;

import com.vschwarzer.baasinga.domain.AbstractBaseAuditEntity;
import com.vschwarzer.baasinga.domain.model.common.RelationType;
import com.vschwarzer.baasinga.domain.model.common.SecurityRoles;

import javax.persistence.*;

/**
 * Entity class for applications.
 *
 * @author <a href="mailto:vs@stroodel.com">Vincent Schwarzer</a>
 */
@Entity
@Table(name = "ba_applicationuser")
public class ApplicationUser extends AbstractBaseAuditEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private SecurityRoles securityRoles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicationId", referencedColumnName = "id")
    private Application application;

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

    public SecurityRoles getSecurityRoles() {
        return securityRoles;
    }

    public void setSecurityRoles(SecurityRoles securityRoles) {
        this.securityRoles = securityRoles;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
