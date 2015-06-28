package com.vschwarzer.baasinga.service.generator;

import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.domain.model.render.Model;
import com.vschwarzer.baasinga.domain.model.render.Repository;
import com.vschwarzer.baasinga.service.common.AbstractService;
import com.vschwarzer.baasinga.service.generator.common.Constants;
import com.vschwarzer.baasinga.service.generator.common.DirectoryUtil;
import com.vschwarzer.baasinga.service.generator.engine.TemplateRenderer;
import com.vschwarzer.baasinga.service.generator.mvn.MavenCompiler;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent Schwarzer on 20.06.15.
 * Generator entry point
 */
@Service
public class ApplicationGeneratorImpl extends AbstractService implements ApplicationGenerator {

    private final Logger LOG = LoggerFactory.getLogger(ApplicationGeneratorImpl.class);

    @Autowired
    private DirectoryUtil directoryUtil;
    @Autowired
    private TemplateRenderer templateRenderer;
    @Autowired
    MavenCompiler mavenCompiler;

    @Override
    public void generateApplication(Application application) {
        createProjectStructure(application);
        addConfig(application);
        renderEntites(application);
        renderRepositories(application);
        compileApplication(application);
    }

    @Override
    public void compileApplication(Application application) {
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

    private void renderEntites(Application application){
        for(Model model : application.getModels()){
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("package", directoryUtil.getPackage(DirectoryUtil.PackageType.Model));
            data.put("imports", model.getImports());
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

    private void renderRepositories(Application application){
        for(Repository repository : application.getRepositories()){
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("package", directoryUtil.getPackage(DirectoryUtil.PackageType.Repository));
            data.put("imports", repository.getImports());
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
}