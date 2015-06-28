package com.vschwarzer.baasinga.service.generator.common;


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

    public enum PackageType {
        Model,
        Repository
    }

    public void createMavenProjectStructure(Application app) throws IOException {
        String mavenRootDir = pathConfig.outputDir + "/" + app.getName();
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
    }

    public String getMavenRootDir(Application application) {
        return pathConfig.outputDir + "/" + application.getName();
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

    public String getPackage(PackageType type) {
        switch (type) {
            case Model:
                return parseToPackage(Constants.ROOT_PACKAGE_NAME + Constants.MODEL_PACKAGE_NAME);
            case Repository:
                return parseToPackage(Constants.ROOT_PACKAGE_NAME + Constants.REPOSITORY_PACKAGE_NAME);
            default:
                return "";
        }
    }

    public String getTemplateDir() {
        return pathConfig.templateRootDir + Constants.TEMPLATE_SUB_DIR;
    }

    private String parseToPackage(String path) {
        return path.replace("/", ".").substring(1);
    }

}
