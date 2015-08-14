package com.vschwarzer.baasinga.web.config.common;

import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.common.DomainType;
import com.vschwarzer.baasinga.domain.model.render.Annotation;
import com.vschwarzer.baasinga.domain.model.render.Import;
import com.vschwarzer.baasinga.domain.model.render.Version;
import com.vschwarzer.baasinga.repository.authorization.UserDAO;
import com.vschwarzer.baasinga.repository.render.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Vincent Schwarzer on 27.06.15.
 */
@Component
public class DataGeneratorUtil {

    @Autowired
    ApplicationDAO applicationDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    RepositoryDAO repositoryDAO;

    @Autowired
    MethodDAO methodDAO;

    @Autowired
    AttributeDAO attributeDAO;

    @Autowired
    VersionDAO versionDAO;

    @Autowired
    ImportDAO importDAO;

    @Autowired
    ModelDAO modelDAO;

    @Autowired
    AnnotationDAO annotationDAO;


    @Transactional
    public void insertCommonData() {

        /**
         * Create system user and first version
         */
        User user = new User();
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setUsername("vs@stroodel.com");
        user.setFirstName("Vincent");
        user.setLastName("Schwarzer");
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("abc"));
        userDAO.create(user);

        Version version = new Version();
        version.setName("1.0");
        version.setVersionNumber(1);
        version.setDescription("TestVersion");
        version.setCreatedBy(user);
        versionDAO.create(version);

        String[] importArray = {
                "org.springframework.data.repository.CrudRepository",
                "org.springframework.security.access.prepost.PreAuthorize",
                "javax.persistence.Entity",
                "org.springframework.data.rest.core.annotation.RestResource",
                "javax.persistence.ManyToOne",
                "javax.persistence.OneToOne",
                "javax.persistence.OneToMany",
                "javax.persistence.ManyToMany"
        };

        Map<String, Import> importMap = new HashMap<>();
        for (String packageName : Arrays.asList(importArray)) {
            Import importClass = new Import();
            importClass.setPackageName(packageName);
            importClass.setCreatedBy(user);
            importDAO.create(importClass);
            importMap.put(packageName, importClass);
        }


        Annotation entity = new Annotation();
        entity.setName("@Entity");
        entity.setType(DomainType.MODEL);
        Set<Import> importSet = new HashSet<>();
        importSet.add(importMap.get("javax.persistence.Entity"));
        entity.setImports(importSet);
        entity.setCreatedBy(user);
        annotationDAO.create(entity);

        Annotation repository = new Annotation();
        repository.setName("@RestResource");
        repository.setType(DomainType.REPOSITORY);
        Set<Import> importSet2 = new HashSet<>();
        importSet2.add(importMap.get("org.springframework.data.rest.core.annotation.RestResource"));
        repository.setImports(importSet2);
        repository.setCreatedBy(user);
        annotationDAO.create(repository);

        Annotation repositorySec = new Annotation();
        repositorySec.setName("@PreAuthorize");
        repositorySec.setValue("(\"hasRole('PLACEHOLDER')\")");
        repositorySec.setType(DomainType.REPOSITORY);
        Set<Import> importSet8 = new HashSet<>();
        importSet8.add(importMap.get("org.springframework.security.access.prepost.PreAuthorize"));
        repositorySec.setImports(importSet8);
        repositorySec.setCreatedBy(user);
        annotationDAO.create(repositorySec);

        Annotation manytoone = new Annotation();
        manytoone.setName("@ManyToOne");
        manytoone.setType(DomainType.MODEL);
        Set<Import> importSet3 = new HashSet<>();
        importSet3.add(importMap.get("javax.persistence.ManyToOne"));
        manytoone.setImports(importSet3);
        manytoone.setCreatedBy(user);
        annotationDAO.create(manytoone);

        Annotation onetoone = new Annotation();
        onetoone.setName("@OneToOne");
        onetoone.setType(DomainType.MODEL);
        Set<Import> importSet4 = new HashSet<>();
        importSet4.add(importMap.get("javax.persistence.OneToOne"));
        onetoone.setImports(importSet4);
        onetoone.setCreatedBy(user);
        annotationDAO.create(onetoone);

        Annotation onetomany = new Annotation();
        onetomany.setName("@OneToMany");
        onetomany.setType(DomainType.MODEL);
        Set<Import> importSet5 = new HashSet<>();
        importSet5.add(importMap.get("javax.persistence.OneToMany"));
        onetomany.setImports(importSet5);
        onetomany.setCreatedBy(user);
        annotationDAO.create(onetomany);

        Annotation manytomany = new Annotation();
        manytomany.setName("@ManyToMany");
        manytomany.setType(DomainType.MODEL);
        Set<Import> importSet6 = new HashSet<>();
        importSet6.add(importMap.get("javax.persistence.ManyToMany"));
        manytomany.setImports(importSet6);
        manytomany.setCreatedBy(user);
        annotationDAO.create(manytomany);


//        Application application = new Application();
//        application.setName("TestApp");
//        application.setCloudEnabled(false);
//        application.setSecEnabled(false);
//        application.setVersion(version);
//        application.setUser(user);
//        applicationDAO.create(application);
//
//        Model model = new Model();
//        model.setName("Person");
//        model.setVersion(version);
//        model.setApplication(application);
//        model.setImports(entityImports);
//        model.setAnnotations(entityAnnotations);
//        modelDAO.create(model);
//
//        Model model2 = new Model();
//        model2.setName("Contact");
//        model2.setVersion(version);
//        model2.setApplication(application);
//        model2.setImports(entityImports);
//        //model2.setAnnotations(entityAnnotations);
//        modelDAO.create(model2);
//
//        Relation relation = new Relation();
//        relation.setVersion(version);
//        relation.setChild(model2);
//        relation.setOwner(model);
//        relation.setRelationType(RelationType.OneToMany);
//        relationDAO.create(relation);
//
//        Attribute attribute = new Attribute();
//        attribute.setName("name");
//        attribute.setVersion(version);
//        attribute.setDataType(Attribute.DataType.STRING);
//        attribute.setModel(model);
//        attributeDAO.create(attribute);
//
//        Repository repository = new Repository();
//        repository.setName(model.getName() + "Repository");
//        repository.setModel(model);
//        repository.setVersion(version);
//        repository.setApplication(application);
//        repository.setImports(repositoryImports);
//        repository.setAnnotations(repositoryAnnotations);
//        repositoryDAO.create(repository);

    }
}
