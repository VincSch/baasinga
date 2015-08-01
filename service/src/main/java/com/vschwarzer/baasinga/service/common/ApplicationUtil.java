package com.vschwarzer.baasinga.service.common;

import com.vschwarzer.baasinga.domain.AbstractBaseEntity;
import com.vschwarzer.baasinga.domain.dto.application.AppDTO;
import com.vschwarzer.baasinga.domain.dto.application.AttributeDTO;
import com.vschwarzer.baasinga.domain.dto.application.ModelDTO;
import com.vschwarzer.baasinga.domain.dto.application.RelationDTO;
import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.common.DomainType;
import com.vschwarzer.baasinga.domain.model.common.RelationType;
import com.vschwarzer.baasinga.domain.model.render.*;
import com.vschwarzer.baasinga.repository.render.*;
import com.vschwarzer.baasinga.service.generator.common.DirectoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Vincent Schwarzer on 29.07.15.
 */
@Service
@Transactional
public class ApplicationUtil extends AbstractService {

    @Autowired
    AnnotationDAO annotationDAO;

    @Autowired
    ModelDAO modelDAO;

    @Autowired
    AttributeDAO attributeDAO;

    @Autowired
    DirectoryUtil directoryUtil;

    @Autowired
    ImportDAO importDAO;

    @Autowired
    RepositoryDAO repositoryDAO;

    @Autowired
    VersionDAO versionDAO;

    @Autowired
    ApplicationDAO applicationDAO;

    private final String START_VERSION = "1.0";

    public void createApplication(AppDTO appDTO, User user) {
        Version version = versionDAO.findByName(START_VERSION);
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
        createModels(appDTO, application, user);
    }

    public void createModels(AppDTO appDTO, Application application, User user) {
        Map<Model, List<RelationDTO>> relationDTOMap = new HashMap<>();
        for (ModelDTO modelDTO : appDTO.getModels()) {
            //Create new model set common values
            Model model = new Model();
            model.setName(modelDTO.getName());
            model.setVersion(application.getVersion());
            model.setApplication(application);
            model.setCreatedBy(user);

            //Set Imports
            Set<Import> imports = new HashSet<>();

            //Set Annotations
            Set<Annotation> annotations = new HashSet<>();
            annotations.add(annotationDAO.findByName("@Entity"));
            model.setAnnotations(annotations);
            modelDAO.create(model);
            LOG.info("Model " + model.getName() + " with id=" + model.getId() + " has been created!");
            //Create Repository for this Entity/Model
            createRepository(application, user, model);

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
                Attribute attribute = new Attribute();
                attribute.setAttributeType(Attribute.AttributeType.RELATION);
                attribute.setVersion(application.getVersion());
                attribute.setModel(model);
                attribute.setCreatedBy(user);
                attribute.setRelationType(RelationType.getById(Long.valueOf(relationDTO.getRelationType())));
                attribute.setDataType(Attribute.DataType.MODEL);

                String childId = relationDTO.getChild();
                Model childModel = getChildForRelation(childId, appDTO, relationDTOMap);

                Set<Annotation> annotations = new HashSet<>();
                switch (attribute.getRelationType()) {
                    case OneToOne:
                        attribute.setName(childModel.getName().toLowerCase());
                        annotations.add(annotationDAO.findByName("@OneToOne"));
                        attribute.setAnnotations(annotations);
                        break;
                    case OneToMany:
                        attribute.setName((childModel.getName() + "s").toLowerCase());
                        annotations.add(annotationDAO.findByName("@OneToMany"));
                        attribute.setAnnotations(annotations);
                        break;
                    case ManyToOne:
                        attribute.setName(childModel.getName().toLowerCase());
                        annotations.add(annotationDAO.findByName("@ManyToOne"));
                        attribute.setAnnotations(annotations);
                        break;
                    case ManyToMany:
                        attribute.setName((childModel.getName() + "s").toLowerCase());
                        annotations.add(annotationDAO.findByName("@ManyToMany"));
                        attribute.setAnnotations(annotations);
                        break;
                }

                String modelImportPackage = directoryUtil.getPackage(DomainType.MODEL) + "." + childModel.getName();
                if (model.getImports() == null) {
                    Set<Import> imports = new HashSet<>();
                    imports.add(getOrCreateModelImport(modelImportPackage, user));
                    model.setImports(imports);
                } else {
                    model.getImports().add(getOrCreateModelImport(modelImportPackage, user));
                }
                attribute.setChild(childModel);
                attributeDAO.create(attribute);
                LOG.info("Relation for " + model.getName() + " to " + childModel.getName() + " with type " + attribute.getRelationType().name() + " has been created!");
            }

        }
    }

    private Model getChildForRelation(String childId, AppDTO appDTO, Map<Model, List<RelationDTO>> relationDTOMap) {
        String modelName = appDTO.getModels().get(Integer.valueOf(childId)).getName();
        Model resultModel = null;
        for (Map.Entry<Model, List<RelationDTO>> entry : relationDTOMap.entrySet()) {
            if (entry.getKey().getName().equals(modelName)) {
                resultModel = entry.getKey();
                break;
            }
        }

        return resultModel;
    }

    private void createRepository(Application application, User user, Model model) {
        Repository repository = new Repository();
        repository.setModel(model);
        repository.setVersion(application.getVersion());
        repository.setName(model.getName() + "Repository");
        repository.setCreatedBy(user);
        Set<Annotation> annotations = new HashSet<>();

        if (application.isSecEnabled()) {
            boolean doSomething = true;
        } else {
            annotations.add(annotationDAO.findByName("@RestResource"));
        }

        String modelImportPackage = directoryUtil.getPackage(DomainType.MODEL) + "." + model.getName();
        Import modelImport = getOrCreateModelImport(modelImportPackage, user);
        Set<Import> imports = new HashSet<>();
        imports.add(modelImport);
        repository.setImports(imports);
        repositoryDAO.create(repository);
        LOG.info("Repository " + repository.getName() + " with id=" + repository.getId() + " has been created!");

    }

    private void createAttribute(Application application, AttributeDTO attributeDTO, User user, AbstractBaseEntity entity) {
        Attribute attribute = new Attribute();
        attribute.setDataType(Attribute.DataType.STRING);
        attribute.setName(attributeDTO.getName().toLowerCase());
        attribute.setCreatedBy(user);
        attribute.setModel((entity instanceof Model) ? (Model) entity : null);
        attribute.setRepository((entity instanceof Repository) ? (Repository) entity : null);
        attribute.setVersion(application.getVersion());
        attribute.setAttributeType(Attribute.AttributeType.NORMAL);
        attributeDAO.create(attribute);
        LOG.info("Attribute " + attribute.getName() + " with id=" + attribute.getId() + " has been created!");
    }

    private Import getOrCreateModelImport(String packageName, User user) {
        Import imp = importDAO.findByPackage(packageName);
        if (imp == null) {
            imp = new Import();
            imp.setCreatedBy(user);
            imp.setPackageName(packageName);
            importDAO.create(imp);
        }

        return imp;
    }

    //TODO needs some more logic :)
    private int getNextAvailablePort() {
        Random ra = new Random();
        return ra.nextInt();
    }
}
