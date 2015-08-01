package com.vschwarzer.baasinga.web.controller.common;

import com.vschwarzer.baasinga.domain.dto.application.AppDTO;
import com.vschwarzer.baasinga.domain.dto.application.AttributeDTO;
import com.vschwarzer.baasinga.domain.dto.application.ModelDTO;
import com.vschwarzer.baasinga.domain.dto.application.RelationDTO;
import com.vschwarzer.baasinga.domain.model.common.RelationType;
import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.domain.model.render.Attribute;
import com.vschwarzer.baasinga.domain.model.render.Model;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent Schwarzer on 22.07.15.
 */
@Component
public class RequestHelper {

    private final String ID_SEPERATOR = "-";

    public int getModelIndex(String reqString) {
        return Integer.valueOf(reqString.substring(0, reqString.indexOf(ID_SEPERATOR)));
    }

    public int getAttributeOrRelationIndex(String reqString) {
        return Integer.valueOf(reqString.substring(reqString.indexOf(ID_SEPERATOR) + 1, reqString.length()));
    }

    public List<RelationType> relationTypeList() {
        List<RelationType> relationTypes = new ArrayList<>();
        relationTypes.add(RelationType.OneToOne);
        relationTypes.add(RelationType.ManyToOne);
        relationTypes.add(RelationType.OneToMany);
        relationTypes.add(RelationType.ManyToMany);

        return relationTypes;
    }

    public AppDTO parseToDTO(Application application) {
        AppDTO appDTO = new AppDTO();

        appDTO.setName(application.getName());
        appDTO.setCloudEnabled(application.isCloudEnabled());
        appDTO.setSecEnabled(application.isSecEnabled());
        appDTO.setDescription(application.getDescription());
        appDTO.setVersion(application.getVersion().getName());
        appDTO.setPort(application.getName());
        appDTO.setId(application.getId().toString());

        List<ModelDTO> modelDTOs = new ArrayList<>();
        for (Model model : application.getModels()) {
            ModelDTO modelDTO = new ModelDTO();
            modelDTO.setId(model.getId().toString());
            modelDTO.setName(model.getName());

            List<AttributeDTO> attributeDTOs = new ArrayList<>();
            List<RelationDTO> relationDTOs = new ArrayList<>();
            for (Attribute attribute : model.getAttributes()) {
                if (attribute.getAttributeType().equals(Attribute.AttributeType.NORMAL)) {
                    AttributeDTO attributeDTO = new AttributeDTO();
                    attributeDTO.setId(attribute.getId().toString());
                    attributeDTO.setName(attribute.getName());
                    attributeDTOs.add(attributeDTO);
                } else {
                    RelationDTO relationDTO = new RelationDTO();
                    relationDTO.setId(attribute.getId().toString());
                    relationDTO.setChild(indexOf(application, attribute.getChild()));
                    relationDTO.setOwner(indexOf(application, attribute.getModel()));
                    relationDTO.setRelationType(attribute.getRelationType().name());
                    relationDTOs.add(relationDTO);
                }
            }

            modelDTO.setAttributes(attributeDTOs);
            modelDTO.setRelations(relationDTOs);
            modelDTOs.add(modelDTO);
        }

        appDTO.setModels(modelDTOs);
        return appDTO;
    }

    private String indexOf(Application application, Model model) {
        int i = 0;
        for (Model m : application.getModels()) {
            if (m.equals(model))
                return String.valueOf(i);
            else
                i = i++;
        }
        return "-1";
    }


}
