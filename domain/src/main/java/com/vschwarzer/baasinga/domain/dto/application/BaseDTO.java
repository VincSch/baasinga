package com.vschwarzer.baasinga.domain.dto.application;

        import com.vschwarzer.baasinga.domain.model.common.SecurityRoles;

/**
 * Created by Vincent Schwarzer on 23.07.15.
 */
public class BaseDTO {

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleById(String roleId) {
        return SecurityRoles.getById(Long.valueOf(roleId)).name();
    }
}
