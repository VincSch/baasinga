package com.vschwarzer.baasinga.domain.dto.application;

/**
 * Created by Vincent Schwarzer on 13.08.15.
 */
public class ApplicationUserDTO extends BaseDTO {

    private String name = "";
    private String password = "";
    private String roleId = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
