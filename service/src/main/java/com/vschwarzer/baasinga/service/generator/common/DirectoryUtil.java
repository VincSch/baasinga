package com.vschwarzer.baasinga.service.generator.common;


import com.vschwarzer.baasinga.service.generator.ApplicationGenerator;
import com.vschwarzer.baasinga.service.generator.config.PathConfig;
import com.vschwarzer.baasinga.service.generator.common.Constants;
import com.vschwarzer.baasinga.service.generator.model.ApplicationData;
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

    public void createMavenProjectStructure(ApplicationData app) throws IOException {
        String mavenRootDir = pathConfig.outputDir + "/" + app.getApplicationName();
        String srcMainJavaDir = mavenRootDir + Constants.MVN_JAVA_DIR;
        String srcMainResourceDir = mavenRootDir + Constants.MVN_RESOURCE_DIR;
        String rootPackageDir = srcMainJavaDir +  Constants.ROOT_PACKAGE_NAME;
        String rootPackageModelDir = rootPackageDir +  Constants.MODEL_PACKAGE_NAME;
        String rootPackageRepositoryDir = rootPackageDir +  Constants.REPOSITORY_PACKAGE_NAME;

        FileUtils.forceMkdir(new File(mavenRootDir));
        FileUtils.forceMkdir(new File(srcMainJavaDir));
        FileUtils.forceMkdir(new File(srcMainResourceDir));
        FileUtils.forceMkdir(new File(rootPackageModelDir));
        FileUtils.forceMkdir(new File(rootPackageRepositoryDir));
    }

    public String getMavenRootDir(ApplicationData applicationData){
        return pathConfig.outputDir + "/" + applicationData.getApplicationName();
    }
}
