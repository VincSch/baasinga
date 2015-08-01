package com.vschwarzer.baasinga.domain.dto.application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent Schwarzer on 08.07.15.
 */
public class ModelDTO extends BaseDTO{

    private String name = "";
    private List<AttributeDTO> attributes = new ArrayList<>();
    private List<RelationDTO> relations = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
