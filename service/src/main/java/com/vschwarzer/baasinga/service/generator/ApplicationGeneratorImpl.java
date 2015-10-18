package com.vschwarzer.baasinga.service.generator;

import com.vschwarzer.baasinga.domain.model.common.DomainType;
import com.vschwarzer.baasinga.domain.model.render.*;
import com.vschwarzer.baasinga.service.common.BaseService;
import com.vschwarzer.baasinga.service.generator.common.Constants;
import com.vschwarzer.baasinga.service.generator.common.DirectoryUtil;
import com.vschwarzer.baasinga.service.generator.engine.TemplateRenderer;
import com.vschwarzer.baasinga.service.generator.mvn.MavenCompiler;
import freemarker.template.TemplateException;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Vincent Schwarzer on 20.06.15.
 * Generator entry point
 */
@Service
public class ApplicationGeneratorImpl extends BaseService implements ApplicationGenerator {

    @Autowired
    MavenCompiler mavenCompiler;
    @Autowired
    private DirectoryUtil directoryUtil;
    @Autowired
    private TemplateRenderer templateRenderer;

    @Override
    public void generateApplication(Application application) {
        createProjectStructure(application);
        addConfig(application);
        renderEntites(application);
        renderRepositories(application);
        compileApplication(application);
    }

    @Override
    public String getJarDownloadURL(Application application) {
        String appRootDir = directoryUtil.getMavenRootDir(application);
        String filePath = appRootDir + "/target/" + application.getName() + "-" + application.getVersion().getName() + ".jar";
        return filePath;
    }

    @Override
    public String getSourceDownloadURL(Application application) {
        //create zip
        String destinationPath = directoryUtil.getAppRootDir(application) + application.getName() + "-" + application.getVersion().getName() + "-Source.zip";
        try {
            ZipFile zipFile = new ZipFile(destinationPath);
            zipFile.createZipFileFromFolder(directoryUtil.getMavenRootDir(application), new ZipParameters(), false, 0);
        } catch (ZipException e) {
            LOG.error("Something went wrong while trying to compress the source folder for application " + application.getName() + "-" + application.getVersion().getName());
            e.printStackTrace();
        }
        return destinationPath;
    }

    private void compileApplication(Application application) {
        mavenCompiler.cleanInstall(directoryUtil.getMavenRootDir(application));
    }

    private void createProjectStructure(Application application) {
        try {
            directoryUtil.createMavenProjectStructure(application);
        } catch (IOException e) {
            LOG.error("Something went wrong while maven clean install!");
            e.printStackTrace();
        }
    }

    private void addConfig(Application application) {
        try {
            templateRenderer.renderConfiguration(application);
        } catch (TemplateException e) {
            LOG.error("Something went wrong while config template rendering!");
            e.printStackTrace();
        } catch (IOException e) {
            LOG.error("Something went wrong while config I/O!");
            e.printStackTrace();
        }
    }

    private void renderEntites(Application application) {
        for (Model model : application.getModels()) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("package", directoryUtil.getPackage(DomainType.MODEL));
            data.put("imports", gatherModelImports(model));
            data.put("annotations", model.getAnnotations());
            data.put("className", model.getName());
            data.put("attributes", model.getAttributes());
            String entityOutputDir = directoryUtil.getModelBaseDir(application) + "/" + model.getName() + Constants.JAVA_FILE_ENDING;
            try {
                templateRenderer.render(Constants.ENTITY_TEMPLATE, data, entityOutputDir);
            } catch (IOException e) {
                LOG.error("Something went wrong while entity I/O!");
                e.printStackTrace();
            } catch (TemplateException e) {
                LOG.error("Something went wrong while entity template rendering!");
                e.printStackTrace();
            }
        }
    }

    private void renderRepositories(Application application) {
        for (Repository repository : application.getRepositories()) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("package", directoryUtil.getPackage(DomainType.REPOSITORY));
            data.put("imports", gatherRepositoryImports(repository));
            for (Annotation annotation : repository.getAnnotations()) {
                if (annotation.getName().equals("@PreAuthorize")) {
                    annotation.setValue(annotation.getValue().replace("PLACEHOLDER", "ROLE_" + repository.getModel().getSecurityRoles().name()));
                }
            }
            data.put("annotations", repository.getAnnotations());
            data.put("interfaceName", repository.getName());
            data.put("modelName", repository.getModel().getName());
            String repositoryOutputDir = directoryUtil.getRepositoryBaseDir(application) + "/" + repository.getName() + Constants.JAVA_FILE_ENDING;
            try {
                templateRenderer.render(Constants.REPOSITORY_TEMPLATE, data, repositoryOutputDir);
            } catch (IOException e) {
                LOG.error("Something went wrong while entity I/O!");
                e.printStackTrace();
            } catch (TemplateException e) {
                LOG.error("Something went wrong while entity template rendering!");
                e.printStackTrace();
            }
        }
    }

    private Set<Import> gatherModelImports(Model model) {
        Set<Import> imports = new HashSet<>();
        imports.addAll(model.getImports());
        for (Annotation annotation : model.getAnnotations()) {
            imports.addAll(annotation.getImports());
        }

        for (Attribute attribute : model.getAttributes()) {
            for (Annotation annotation : attribute.getAnnotations()) {
                imports.addAll(annotation.getImports());
            }
        }

        return imports;
    }

    private Set<Import> gatherRepositoryImports(Repository repository) {
        Set<Import> imports = new HashSet<>();
        imports.addAll(repository.getImports());
        for (Annotation annotation : repository.getAnnotations()) {
            imports.addAll(annotation.getImports());
        }

        for (Attribute attribute : repository.getAttributes()) {
            for (Annotation annotation : attribute.getAnnotations()) {
                imports.addAll(annotation.getImports());
            }
        }

        for (Method method : repository.getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                imports.addAll(annotation.getImports());
            }
        }

        return imports;
    }
}
