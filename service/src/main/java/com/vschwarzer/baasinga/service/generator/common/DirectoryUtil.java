package com.vschwarzer.baasinga.service.generator.common;


import com.vschwarzer.baasinga.domain.model.common.DomainType;
import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.service.generator.config.PathConfig;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Created by Vincent Schwarzer on 20.06.15.
 */
@Component
public class DirectoryUtil {

    @Autowired
    PathConfig pathConfig;

    public void createMavenProjectStructure(Application app) throws IOException {
        String mavenRootDir = getMavenRootDir(app);
        String srcMainJavaDir = mavenRootDir + Constants.MVN_JAVA_DIR;
        String srcMainResourceDir = mavenRootDir + Constants.MVN_RESOURCE_DIR;
        String rootPackageDir = srcMainJavaDir + Constants.ROOT_PACKAGE_NAME;
        String rootPackageModelDir = rootPackageDir + Constants.MODEL_PACKAGE_NAME;
        String rootPackageRepositoryDir = rootPackageDir + Constants.REPOSITORY_PACKAGE_NAME;

        FileUtils.forceMkdir(new File(mavenRootDir));
        FileUtils.forceMkdir(new File(srcMainJavaDir));
        FileUtils.forceMkdir(new File(srcMainResourceDir));
        FileUtils.forceMkdir(new File(rootPackageModelDir));
        FileUtils.forceMkdir(new File(rootPackageRepositoryDir));

        //add logging
        File sourceFile = new File(getTemplateDir() + "/" + Constants.LOG4J_PROPERTIES);
        File destFile = new File(getResourceDir(app) + "/" + Constants.LOG4J_PROPERTIES);
        FileUtils.copyFile(sourceFile, destFile);

        //add application properties
        File sourcePropFile = new File(getTemplateDir() + "/" + Constants.APPLICATION_PROPERTIES);
        File destPropFile = new File(getResourceDir(app) + "/" + Constants.APPLICATION_PROPERTIES);
        FileUtils.copyFile(sourcePropFile, destPropFile);
    }

    public String getMavenRootDir(Application application) {
        return pathConfig.outputDir + "/" + application.getId() + "/" + application.getName() + "-" + application.getVersion().getName();
    }

    public String getResourceDir(Application application) {
        return getMavenRootDir(application) + Constants.MVN_RESOURCE_DIR;
    }

    public String getJavaMaineDir(Application application) {
        return getMavenRootDir(application) + Constants.MVN_JAVA_DIR;
    }

    public String getApiBaseDir(Application application) {
        return getMavenRootDir(application) + Constants.MVN_JAVA_DIR + Constants.ROOT_PACKAGE_NAME;
    }

    public String getModelBaseDir(Application application) {
        return getMavenRootDir(application) + Constants.MVN_JAVA_DIR + Constants.ROOT_PACKAGE_NAME + Constants.MODEL_PACKAGE_NAME;
    }

    public String getRepositoryBaseDir(Application application) {
        return getMavenRootDir(application) + Constants.MVN_JAVA_DIR + Constants.ROOT_PACKAGE_NAME + Constants.REPOSITORY_PACKAGE_NAME;
    }


    public String getRootPackage() {
        return Constants.ROOT_PACKAGE_NAME.replace("/", ".").substring(1);
    }

    public String getPackage(DomainType type) {
        switch (type) {
            case MODEL:
                return parseToPackage(Constants.ROOT_PACKAGE_NAME + Constants.MODEL_PACKAGE_NAME);
            case REPOSITORY:
                return parseToPackage(Constants.ROOT_PACKAGE_NAME + Constants.REPOSITORY_PACKAGE_NAME);
            default:
                return "";
        }
    }

    public String getTemplateDir() {
        return pathConfig.templateRootDir + Constants.TEMPLATE_SUB_DIR;
    }

    public String getAppRootDir(Application application) {
        return pathConfig.outputDir + "/" + application.getId() + "/";
    }

    private String parseToPackage(String path) {
        return path.replace("/", ".").substring(1);
    }

}
