package com.vschwarzer.baasinga.domain.dto.application;

/**
 * Created by Vincent Schwarzer on 18.07.15.
 */
public class RelationDTO extends BaseDTO {

    private String owner = "";
    private String child = "";
    private String relationType = "";

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }
}
