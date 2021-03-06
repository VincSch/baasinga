package com.vschwarzer.baasinga.domain.dto.application;

import com.vschwarzer.baasinga.domain.model.common.SecurityRoles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent Schwarzer on 08.07.15.
 */
public class ModelDTO extends BaseDTO {

    private String name = "";
    private String securityRoleId = "";
    private List<AttributeDTO> attributes = new ArrayList<>();
    private List<RelationDTO> relations = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecurityRoleId() {
        return securityRoleId;
    }

    public void setSecurityRoleId(String securityRoleId) {
        this.securityRoleId = securityRoleId;
    }

    public List<AttributeDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDTO> attributes) {
        this.attributes = attributes;
    }

    public List<RelationDTO> getRelations() {
        return relations;
    }

    public void setRelations(List<RelationDTO> relations) {
        this.relations = relations;
    }

}
