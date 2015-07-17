package com.vschwarzer.baasinga.web.config.common;

import com.vschwarzer.baasinga.domain.model.authentication.User;
import com.vschwarzer.baasinga.domain.model.common.DomainType;
import com.vschwarzer.baasinga.domain.model.render.*;
import com.vschwarzer.baasinga.repository.authorization.UserDAO;
import com.vschwarzer.baasinga.repository.render.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


    public void addTestApplication() {
        String[] entityAnnotationArray = {
                "@Entity"
        };

        String[] repositoryAnnoationArray = {
                "@RestResource"
        };

        String[] repositoryImportArray = {
                "com.baasinga.api.model.Person",
                "org.springframework.data.repository.CrudRepository",
        };

        String[] entityImportArray = {

        };

        String[] annotationImportArray = {
                "javax.persistence.Entity",
                "org.springframework.data.rest.core.annotation.RestResource"

        };

        List<Annotation> entityAnnotations = new ArrayList<>();
        List<Annotation> repositoryAnnotations = new ArrayList<>();

        for (String an : Arrays.asList(entityAnnotationArray)) {
            Annotation annotation = new Annotation();
            annotation.setName(an);
            annotation.setType(DomainType.MODEL);

            List<Import> imports = new ArrayList();
            Import importModel = new Import();
            importModel.setPackageName(annotationImportArray[0]);
            importDAO.create(importModel);
            imports.add(importModel);
            annotation.setImports(imports);
            annotationDAO.create(annotation);
            entityAnnotations.add(annotation);
        }

        for (String an : Arrays.asList(repositoryAnnoationArray)) {
            Annotation annotation = new Annotation();
            annotation.setName(an);
            annotation.setType(DomainType.REPOSITORY);
            List<Import> imports = new ArrayList();
            Import importModel = new Import();
            importModel.setPackageName(annotationImportArray[1]);
            importDAO.create(importModel);
            imports.add(importModel);
            annotation.setImports(imports);
            annotationDAO.create(annotation);
            repositoryAnnotations.add(annotation);
        }


        List<Import> entityImports = new ArrayList<>();
        List<Import> repositoryImports = new ArrayList<>();

        for (String packageName : Arrays.asList(entityImportArray)) {
            Import importClass = new Import();
            importClass.setPackageName(packageName);
            importDAO.create(importClass);
            entityImports.add(importClass);
        }

        for (String packageName : Arrays.asList(repositoryImportArray)) {
            Import importClass = new Import();
            importClass.setPackageName(packageName);
            importDAO.create(importClass);
            repositoryImports.add(importClass);
        }

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
        version.setDescription("TestVersion");
        versionDAO.create(version);

        Application application = new Application();
        application.setName("TestApp");
        application.setCloudEnabled(false);
        application.setSecEnabled(false);
        application.setVersion(version);
        application.setUser(user);
        applicationDAO.create(application);

        Model model = new Model();
        model.setName("Person");
        model.setVersion(version);
        model.setApplication(application);
        model.setImports(entityImports);
        model.setAnnotations(entityAnnotations);
        modelDAO.create(model);

        Attribute attribute = new Attribute();
        attribute.setName("name");
        attribute.setVersion(version);
        attribute.setDataType(Attribute.DataType.STRING);
        attribute.setModel(model);
        attributeDAO.create(attribute);

        Repository repository = new Repository();
        repository.setName(model.getName() + "Repository");
        repository.setModel(model);
        repository.setVersion(version);
        repository.setApplication(application);
        repository.setImports(repositoryImports);
        repository.setAnnotations(repositoryAnnotations);
        repositoryDAO.create(repository);

    }
}
