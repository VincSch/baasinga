package com.vschwarzer.baasinga.service.util;

import com.vschwarzer.baasinga.domain.dto.application.*;
import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.render.*;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Vincent Schwarzer on 29.07.15.
 */
@Component
@Transactional
public class ApplicationCreateUtil extends BaseUtil {

    private final int START_VERSION = 1;

    public void createApplication(AppDTO appDTO, User user) {
        Version version = versionDAO.findByVersionNumber(START_VERSION);
        Application application = new Application();
        application.setUser(user);
        application.setCreatedBy(user);
        application.setCloudEnabled(appDTO.getCloudEnabled());
        application.setSecEnabled(appDTO.getSecEnabled());
        application.setName(appDTO.getName());
        application.setDescription(appDTO.getDescription());
        application.setVersion(version);
        application.setPort(getNextAvailablePort());
        applicationDAO.create(application);
        LOG.info("Application " + application.getName() + " with id=" + application.getId() + " has been created!");
        for (ApplicationUserDTO applicationUserDTO : appDTO.getApplicationUsers()) {
            createOrUpdateApiUser(application, applicationUserDTO, user, null);
        }
        createModels(appDTO, application, user);
    }

    public void createModels(AppDTO appDTO, Application application, User user) {
        Map<Model, List<RelationDTO>> relationDTOMap = new HashMap<>();
        for (ModelDTO modelDTO : appDTO.getModels()) {
            Model model = createModel(application, modelDTO, user);
            //Create Repository for this Entity/Model
            createOrUpdateRepository(application, user, model, null);
            //Create the Attributes for this Entity/Model
            for (AttributeDTO attributeDTO : modelDTO.getAttributes()) {
                createAttribute(application, attributeDTO, user, model);
            }
            relationDTOMap.put(model, modelDTO.getRelations());
        }

        //After all models have been created, create the corresponding relations
        createRelations(appDTO, application, user, relationDTOMap);

    }

    private void createRelations(AppDTO appDTO, Application application, User user, Map<Model, List<RelationDTO>> relationDTOMap) {
        for (Map.Entry<Model, List<RelationDTO>> entry : relationDTOMap.entrySet()) {
            Model model = entry.getKey();
            List<RelationDTO> relationDTOs = entry.getValue();
            for (RelationDTO relationDTO : relationDTOs) {
                createRelation(application, appDTO, model, user, relationDTO, relationDTOMap);
            }

        }
    }


    //TODO needs some more logic :)
    private int getNextAvailablePort() {
        Random ra = new Random();
        return ra.nextInt();
    }
}
