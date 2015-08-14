package com.vschwarzer.baasinga.service.util;

import com.vschwarzer.baasinga.domain.AbstractBaseEntity;
import com.vschwarzer.baasinga.domain.dto.application.*;
import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.common.DomainType;
import com.vschwarzer.baasinga.domain.model.common.RelationType;
import com.vschwarzer.baasinga.domain.model.common.SecurityRoles;
import com.vschwarzer.baasinga.domain.model.render.*;
import com.vschwarzer.baasinga.repository.render.*;
import com.vschwarzer.baasinga.service.generator.common.DirectoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Vincent Schwarzer on 02.08.15.
 */
public class BaseUtil {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected VersionDAO versionDAO;
    @Autowired
    protected ApplicationDAO applicationDAO;
    @Autowired
    protected AnnotationDAO annotationDAO;
    @Autowired
    protected DirectoryUtil directoryUtil;
    @Autowired
    protected ModelDAO modelDAO;
    @Autowired
    protected AttributeDAO attributeDAO;
    @Autowired
    protected ImportDAO importDAO;
    @Autowired
    protected RepositoryDAO repositoryDAO;
    @Autowired
    protected ApplicationUserDAO applicationUserDAO;


    protected Model createModel(Application application, ModelDTO modelDTO, User user) {
        Model model = new Model();
        model.setName(modelDTO.getName());
        model.setVersion(application.getVersion());
        model.setApplication(application);
        model.setCreatedBy(user);
        model.setSecurityRoles(SecurityRoles.getById(Long.valueOf(modelDTO.getSecurityRoleId())));

        //Set Imports
        Set<Import> imports = new HashSet<>();

        //Set Annotations
        Set<Annotation> annotations = new HashSet<>();
        annotations.add(annotationDAO.findByName("@Entity"));
        model.setAnnotations(annotations);
        modelDAO.create(model);
        LOG.info("Model " + model.getName() + " with id=" + model.getId() + " has been created!");
        return model;
    }

    protected ApplicationUser createOrUpdateApiUser(Application application, ApplicationUserDTO applicationUserDTO, User user, ApplicationUser apiUser) {
        ApplicationUser applicationUser;
        applicationUser = (apiUser == null) ? new ApplicationUser() : apiUser;
        applicationUser.setUsername(applicationUserDTO.getName());
        applicationUser.setPassword(applicationUserDTO.getPassword());
        applicationUser.setVersion(application.getVersion());
        applicationUser.setApplication(application);
        applicationUser.setSecurityRoles(SecurityRoles.getById(Long.valueOf(applicationUserDTO.getRoleId())));
        if (applicationUser.getId() == null) {
            applicationUser.setCreatedBy(user);
            applicationUserDAO.create(applicationUser);
        }
        {
            applicationUser.setUpdatedBy(user);
            applicationUserDAO.update(applicationUser);
        }
        return applicationUser;
    }

    protected Attribute createAttribute(Application application, AttributeDTO attributeDTO, User user, AbstractBaseEntity entity) {
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
        return attribute;
    }

    protected void createOrUpdateRepository(Application application, User user, Model model, Repository repo) {
        Annotation secAnnotation = annotationDAO.findByName("@PreAuthorize");
        Repository repository;
        repository = (repo == null) ? new Repository() : repo;
        repository.setModel(model);
        repository.setApplication(application);
        repository.setVersion(application.getVersion());
        repository.setName(model.getName() + "Repository");
        if (repository.getId() == null)
            repository.setCreatedBy(user);
        else
            repository.setUpdatedBy(user);

        if (repository.getId() == null) {
            Set<Annotation> annotations = new HashSet<>();
            if (application.isSecEnabled() && !model.getSecurityRoles().getId().equals(SecurityRoles.NONE.getId())) {
                annotations.add(secAnnotation);
            }
            annotations.add(annotationDAO.findByName("@RestResource"));
            String modelImportPackage = directoryUtil.getPackage(DomainType.MODEL) + "." + model.getName();
            Import modelImport = getOrCreateModelImport(modelImportPackage, user);
            Set<Import> imports = new HashSet<>();
            imports.add(modelImport);
            repository.setAnnotations(annotations);
            repository.setImports(imports);
            repositoryDAO.create(repository);
            LOG.info("Repository " + repository.getName() + " with id=" + repository.getId() + " has been created!");
        } else {
            if (!application.isSecEnabled() || model.getSecurityRoles().getId().equals(SecurityRoles.NONE.getId())) {
                if (repository.getAnnotations().contains(secAnnotation))
                    repository.getAnnotations().remove(secAnnotation);
            }

            if (application.isSecEnabled() && !model.getSecurityRoles().getId().equals(SecurityRoles.NONE.getId())) {
                repository.getAnnotations().add(secAnnotation);
            }

            repositoryDAO.update(repository);
            LOG.info("Repository " + repository.getName() + " with id=" + repository.getId() + " has been updated!");
        }
    }

    protected Attribute createRelation(Application application, AppDTO appDTO, Model model, User user, RelationDTO relationDTO, Map<Model, List<RelationDTO>> relationDTOMap) {
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
        return attribute;
    }

    protected Model getChildForRelation(String childId, AppDTO appDTO, Map<Model, List<RelationDTO>> relationDTOMap) {
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

    protected Import getOrCreateModelImport(String packageName, User user) {
        Import imp = importDAO.findByPackage(packageName);
        if (imp == null) {
            imp = new Import();
            imp.setCreatedBy(user);
            imp.setPackageName(packageName);
            importDAO.create(imp);
        }

        return imp;
    }
}
