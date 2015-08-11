package com.vschwarzer.baasinga.service.util;

import com.vschwarzer.baasinga.domain.dto.application.AppDTO;
import com.vschwarzer.baasinga.domain.dto.application.AttributeDTO;
import com.vschwarzer.baasinga.domain.dto.application.ModelDTO;
import com.vschwarzer.baasinga.domain.dto.application.RelationDTO;
import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.common.DomainType;
import com.vschwarzer.baasinga.domain.model.common.RelationType;
import com.vschwarzer.baasinga.domain.model.history.*;
import com.vschwarzer.baasinga.domain.model.render.*;
import com.vschwarzer.baasinga.repository.history.ApplicationTraceDAO;
import com.vschwarzer.baasinga.repository.history.AttributeTraceDAO;
import com.vschwarzer.baasinga.repository.history.ModelTraceDAO;
import com.vschwarzer.baasinga.repository.history.RepositoryTraceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Vincent Schwarzer on 02.08.15.
 */
@Component
@Transactional
public class ApplicationUpdateUtil extends BaseUtil {

    @Autowired
    ApplicationTraceDAO applicationTraceDAO;
    @Autowired
    ModelTraceDAO modelTraceDAO;
    @Autowired
    RepositoryTraceDAO repositoryTraceDAO;
    @Autowired
    AttributeTraceDAO attributeTraceDAO;

    public void updateApplication(AppDTO appDTO, User user) {
        Version releaseVersion = releaseNewVersion(appDTO, user);
        Application application = applicationDAO.findByUserAndId(user, Long.valueOf(appDTO.getId()));
        ApplicationTrace applicationTrace = createApplicationTrace(application);
        application.setUpdatedBy(user);
        application.setCloudEnabled(appDTO.getCloudEnabled());
        application.setSecEnabled(appDTO.getSecEnabled());
        application.setName(appDTO.getName());
        application.setDescription(appDTO.getDescription());
        application.setVersion(releaseVersion);
        applicationDAO.update(application);
        LOG.info("Application " + application.getName() + " with id=" + application.getId() + " has been updated!");
        updateModels(appDTO, application, applicationTrace, user);
    }

    public void updateModels(AppDTO appDTO, Application application, ApplicationTrace applicationTrace, User user) {
        Map<Model, List<RelationDTO>> relationDTOMap = new HashMap<>();
        for (ModelDTO modelDTO : appDTO.getModels()) {
            Model model = null;
            ModelTrace modelTrace = null;
            if (modelDTO.getId().isEmpty()) {
                model = createModel(application, modelDTO, user);
                createOrUpdateRepository(application, user, model, null);
            } else {
                //Update model with common values
                model = modelDAO.findOne(Long.valueOf(modelDTO.getId()));
                model.setName(modelDTO.getName());
                model.setVersion(application.getVersion());
                model.setApplication(application);
                model.setUpdatedBy(user);
                modelDAO.update(model);
                LOG.info("Model " + model.getName() + " with id=" + model.getId() + " has been updated!");
                modelTrace = createModelTrace(user, model, applicationTrace);
                //Update corresponding Repository
                Repository repository = repositoryDAO.findByAppAndModel(application.getId(), model.getId());
                createOrUpdateRepository(application, user, model, repository);
                createRepositoryTrace(user, repository, applicationTrace, modelTrace);
            }


            //Update the Attributes for this Entity/Model
            for (AttributeDTO attributeDTO : modelDTO.getAttributes()) {
                if (attributeDTO.getId().isEmpty()) {
                    createAttribute(application, attributeDTO, user, model);
                } else {
                    Attribute attribute = attributeDAO.findOne(Long.valueOf(attributeDTO.getId()));
                    attribute.setName(attributeDTO.getName().toLowerCase());
                    attribute.setModel(model);
                    attribute.setCreatedBy(user);
                    attribute.setVersion(application.getVersion());
                    attribute.setAttributeType(Attribute.AttributeType.NORMAL);
                    attributeDAO.update(attribute);
                    LOG.info("Attribute " + attribute.getName() + " with id=" + attribute.getId() + " has been updated!");
                    createAttributeTrace(applicationTrace, user, modelTrace, attribute);
                }
            }

            relationDTOMap.put(model, modelDTO.getRelations());
        }

        //After all models have been updated, update the corresponding relations
        updateRelations(appDTO, application, applicationTrace, user, relationDTOMap);
    }

    private void updateRelations(AppDTO appDTO, Application application, ApplicationTrace applicationTrace, User user, Map<Model, List<RelationDTO>> relationDTOMap) {
        for (Map.Entry<Model, List<RelationDTO>> entry : relationDTOMap.entrySet()) {
            Model model = entry.getKey();
            List<RelationDTO> relationDTOs = entry.getValue();
            for (RelationDTO relationDTO : relationDTOs) {
                if (relationDTO.getId().isEmpty()) {
                    createRelation(application, appDTO, model, user, relationDTO, relationDTOMap);
                } else {
                    Attribute relation = attributeDAO.findOne(Long.valueOf(relationDTO.getId()));
                    relation.setAttributeType(Attribute.AttributeType.RELATION);
                    relation.setVersion(application.getVersion());
                    relation.setModel(model);
                    relation.setCreatedBy(user);
                    relation.setRelationType(RelationType.getById(Long.valueOf(relationDTO.getRelationType())));
                    relation.setDataType(Attribute.DataType.MODEL);
                    String childId = relationDTO.getChild();
                    Model childModel = getChildForRelation(childId, appDTO, relationDTOMap);

                    //Relation to other model has been changed
                    if (!relation.getChild().equals(childModel)) {
                        Annotation annotation = null;

                        switch (relation.getRelationType()) {
                            case OneToOne:
                                annotation = annotationDAO.findByName("@OneToOne");
                                break;
                            case OneToMany:
                                annotation = annotationDAO.findByName("@OneToMany");
                                break;
                            case ManyToOne:
                                annotation = annotationDAO.findByName("@ManyToOne");
                                break;
                            case ManyToMany:
                                annotation = annotationDAO.findByName("@ManyToMany");
                                break;
                        }

                        relation.getAnnotations().remove(annotation);
                        String modelImportPackageToRemove = directoryUtil.getPackage(DomainType.MODEL) + "." + relation.getChild().getName();
                        Import importToRemove = getOrCreateModelImport(modelImportPackageToRemove, user);
                        model.getImports().remove(importToRemove);

                        Set<Annotation> annotations = new HashSet<>();
                        switch (relation.getRelationType()) {
                            case OneToOne:
                                relation.setName(childModel.getName().toLowerCase());
                                annotations.add(annotationDAO.findByName("@OneToOne"));
                                relation.setAnnotations(annotations);
                                break;
                            case OneToMany:
                                relation.setName((childModel.getName() + "s").toLowerCase());
                                annotations.add(annotationDAO.findByName("@OneToMany"));
                                relation.setAnnotations(annotations);
                                break;
                            case ManyToOne:
                                relation.setName(childModel.getName().toLowerCase());
                                annotations.add(annotationDAO.findByName("@ManyToOne"));
                                relation.setAnnotations(annotations);
                                break;
                            case ManyToMany:
                                relation.setName((childModel.getName() + "s").toLowerCase());
                                annotations.add(annotationDAO.findByName("@ManyToMany"));
                                relation.setAnnotations(annotations);
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
                        relation.setChild(childModel);
                        attributeDAO.update(relation);
                        LOG.info("Relation for " + model.getName() + " to " + childModel.getName() + " with type " + relation.getRelationType().name() + " has been created!");

                        ModelTrace owner = modelTraceDAO.findByAppTraceAndModelId(applicationTrace.getId(), relation.getModel().getId());
                        createAttributeTrace(applicationTrace, user, owner, relation);
                    }
                }

            }

        }
    }


    private ApplicationTrace createApplicationTrace(Application application) {
        ApplicationTrace applicationTrace = new ApplicationTrace();
        applicationTrace.setParentId(application.getId());
        applicationTrace.setName(application.getName());
        applicationTrace.setPort(application.getPort());
        applicationTrace.setCloudEnabled(application.isCloudEnabled());
        applicationTrace.setSecEnabled(application.isSecEnabled());
        applicationTrace.setVersion(application.getVersion());
        applicationTrace.setUser(application.getUser());
        applicationTrace.setCreatedBy(application.getUser());
        applicationTrace.setDescription(application.getDescription());
        applicationTraceDAO.create(applicationTrace);
        LOG.info("ApplicationTrace " + applicationTrace.getName() + " with id=" + applicationTrace.getId() + " has been created!");
        return applicationTrace;
    }

    private ModelTrace createModelTrace(User user, Model model, ApplicationTrace applicationTrace) {
        ModelTrace modelTrace = new ModelTrace();
        modelTrace.setName(model.getName());
        modelTrace.setParentId(model.getId());
        modelTrace.setVersion(applicationTrace.getVersion());
        modelTrace.setImports(new HashSet<Import>(model.getImports()));
        modelTrace.setCreatedBy(user);
        modelTraceDAO.create(modelTrace);
        return modelTrace;
    }

    private RepositoryTrace createRepositoryTrace(User user, Repository repository, ApplicationTrace applicationTrace, ModelTrace modelTrace) {
        RepositoryTrace repositoryTrace = new RepositoryTrace();
        repositoryTrace.setParentId(repository.getId());
        repositoryTrace.setVersion(applicationTrace.getVersion());
        repositoryTrace.setName(repository.getName());
        repositoryTrace.setCreatedBy(user);
        repositoryTrace.setImports(new HashSet<Import>(repository.getImports()));
        repositoryTrace.setAnnotations(new HashSet<Annotation>(repository.getAnnotations()));
        repositoryTrace.setModel(modelTrace);

        //TODO parse methods, currently methods are not used at all!
//        Set<MethodTrace> methodTraces = new HashSet<>();
//        for(Method method : repository.getMethods()){
//            MethodTrace methodTrace = new MethodTrace();
//            methodTrace.setVersion();
//            methodTrace.setName();
//            methodTrace.set
//        }

        repositoryTrace.setApplication(applicationTrace);
        repositoryTraceDAO.create(repositoryTrace);
        return repositoryTrace;
    }

    private AttributeTrace createAttributeTrace(ApplicationTrace applicationTrace, User user, ModelTrace modelTrace, Attribute attribute) {
        AttributeTrace attributeTrace = new AttributeTrace();
        attributeTrace.setName(attribute.getName());
        attributeTrace.setDataType(attribute.getDataType());
        attributeTrace.setAttributeType(attribute.getAttributeType());
        if (attribute.getAttributeType().equals(Attribute.AttributeType.RELATION)) {
            //find the model trace for this relation child by the trace app id and original model id
            ModelTrace child = modelTraceDAO.findByAppTraceAndModelId(applicationTrace.getId(), attribute.getChild().getId());
            attributeTrace.setChild(child);
            attributeTrace.setRelationType(attribute.getRelationType());
        }
        attributeTrace.setModel(modelTrace);
        attributeTrace.setAnnotations(new HashSet<Annotation>(attribute.getAnnotations()));
        attributeTrace.setVersion(applicationTrace.getVersion());
        attributeTrace.setCreatedBy(user);
        attributeTraceDAO.create(attributeTrace);
        return attributeTrace;
    }


    private Version releaseNewVersion(AppDTO appDTO, User user) {
        Version oldVersion = versionDAO.findByName(appDTO.getVersion());
        int newVersionNumber = oldVersion.getVersionNumber() + 1;
        Version newVersion = versionDAO.findByVersionNumber(newVersionNumber);
        if (newVersion == null) {
            newVersion = new Version();
            String newVersionName = String.valueOf(newVersionNumber) + ".0";
            newVersion.setVersionNumber(newVersionNumber);
            newVersion.setName(newVersionName);
            newVersion.setCreatedBy(user);
            versionDAO.create(newVersion);
        }
        return newVersion;
    }
}
