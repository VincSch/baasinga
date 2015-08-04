package com.vschwarzer.baasinga.web.controller.common;

import com.vschwarzer.baasinga.domain.AbstractBaseEntity;
import com.vschwarzer.baasinga.domain.dto.application.AppDTO;
import com.vschwarzer.baasinga.domain.dto.application.AttributeDTO;
import com.vschwarzer.baasinga.domain.dto.application.ModelDTO;
import com.vschwarzer.baasinga.domain.dto.application.RelationDTO;
import com.vschwarzer.baasinga.domain.dto.common.ApplicationStatisticDTO;
import com.vschwarzer.baasinga.domain.model.common.RelationType;
import com.vschwarzer.baasinga.domain.model.history.ApplicationTrace;
import com.vschwarzer.baasinga.domain.model.history.AttributeTrace;
import com.vschwarzer.baasinga.domain.model.history.ModelTrace;
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
        appDTO.setCreatedAt(application.getCreatedAt());
        appDTO.setUpdatedAt(application.getUpdatedAt());
        appDTO.setPort(String.valueOf(application.getPort()));
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

    public List<AppDTO> parseToDTO(List<ApplicationTrace> applicationTraces) {
        List<AppDTO> appDTOList = new ArrayList<>();
        for (ApplicationTrace applicationTrace : applicationTraces) {
            ApplicationStatisticDTO applicationStatisticDTO = new ApplicationStatisticDTO();
            int index = applicationTraces.indexOf(applicationTrace);
            AppDTO applicationDTO = parseToDTO(applicationTrace);
            if (index == 0) {
                applicationStatisticDTO.setModelChange("not available");
            } else {
                applicationStatisticDTO = createHistoryStatistic(applicationTrace, applicationTraces.get(index - 1));
            }

            applicationDTO.setApplicationStatistic(applicationStatisticDTO);
            appDTOList.add(applicationDTO);
        }
        return appDTOList;
    }


    public AppDTO parseToDTO(ApplicationTrace applicationTrace) {
        AppDTO appDTO = new AppDTO();

        appDTO.setName(applicationTrace.getName());
        appDTO.setCloudEnabled(applicationTrace.isCloudEnabled());
        appDTO.setSecEnabled(applicationTrace.isSecEnabled());
        appDTO.setDescription(applicationTrace.getDescription());
        appDTO.setVersion(applicationTrace.getVersion().getName());
        appDTO.setCreatedAt(applicationTrace.getCreatedAt());
        appDTO.setUpdatedAt(applicationTrace.getUpdatedAt());
        appDTO.setPort(String.valueOf(applicationTrace.getPort()));
        appDTO.setId(applicationTrace.getId().toString());
        appDTO.setParentId(String.valueOf(applicationTrace.getParentId()));

        List<ModelDTO> modelDTOs = new ArrayList<>();
        for (ModelTrace modelTrace : applicationTrace.getModels()) {
            ModelDTO modelDTO = new ModelDTO();
            modelDTO.setId(modelTrace.getId().toString());
            modelDTO.setName(modelTrace.getName());

            List<AttributeDTO> attributeDTOs = new ArrayList<>();
            List<RelationDTO> relationDTOs = new ArrayList<>();
            for (AttributeTrace attributeTrace : modelTrace.getAttributes()) {
                if (attributeTrace.getAttributeType().equals(Attribute.AttributeType.NORMAL)) {
                    AttributeDTO attributeDTO = new AttributeDTO();
                    attributeDTO.setId(attributeTrace.getId().toString());
                    attributeDTO.setName(attributeTrace.getName());
                    attributeDTOs.add(attributeDTO);
                } else {
                    RelationDTO relationDTO = new RelationDTO();
                    relationDTO.setId(attributeTrace.getId().toString());
                    relationDTO.setChild(indexOf(applicationTrace, attributeTrace.getChild()));
                    relationDTO.setOwner(indexOf(applicationTrace, attributeTrace.getModel()));
                    relationDTO.setRelationType(attributeTrace.getRelationType().name());
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

    public ApplicationStatisticDTO createHistoryStatistic(AbstractBaseEntity origin, ApplicationTrace olderApplicationTrace) {
        ApplicationStatisticDTO applicationStatisticDTO = new ApplicationStatisticDTO();

        //Differences between models
        int modelChangeCount = (origin instanceof ApplicationTrace)
                ? ((ApplicationTrace) origin).getModels().size() : ((Application) origin).getModels().size() - olderApplicationTrace.getModels().size();
        String modelChange = "";
        if (modelChangeCount > 0) {
            modelChange += "+ ";
            modelChange = modelChange + String.valueOf(modelChangeCount) + " Model";
        } else if (modelChangeCount == 0) {
            modelChange = "no changes";
        } else {
            modelChange += String.valueOf(modelChangeCount) + " Model";
        }

        applicationStatisticDTO.setModelChange(modelChange);
        return applicationStatisticDTO;
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

    private String indexOf(ApplicationTrace application, ModelTrace model) {
        int i = 0;
        for (ModelTrace m : application.getModels()) {
            if (m.equals(model))
                return String.valueOf(i);
            else
                i = i++;
        }
        return "-1";
    }


}
