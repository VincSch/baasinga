package com.vschwarzer.baasinga.service.generator;

import com.vschwarzer.baasinga.service.common.AbstractService;
import com.vschwarzer.baasinga.service.generator.engine.TemplateRenderer;
import com.vschwarzer.baasinga.service.generator.model.ApplicationData;
import com.vschwarzer.baasinga.service.generator.common.DirectoryUtil;
import com.vschwarzer.baasinga.service.generator.mvn.MavenCompiler;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Vincent Schwarzer on 20.06.15.
 * Generator entry point
 */
@Service
public class ApplicationGeneratorImpl extends AbstractService implements ApplicationGenerator{

    @Autowired
    private DirectoryUtil directoryUtil;
    @Autowired
    private TemplateRenderer templateRenderer;
    @Autowired
    MavenCompiler mavenCompiler;

    @Override
    public boolean generateApplication(String pathToResource) {
        ApplicationData appData = new ApplicationData();
        appData.setApplicationName("TestApp");
        try {
            directoryUtil.createMavenProjectStructure(appData);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            templateRenderer.renderConfigClasses(pathToResource, appData);
            templateRenderer.renderPom(pathToResource, appData);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (TemplateException e) {
            e.printStackTrace();
            return false;
        }

        mavenCompiler.mvn(directoryUtil.getMavenRootDir(appData));

        return true;
    }
}
