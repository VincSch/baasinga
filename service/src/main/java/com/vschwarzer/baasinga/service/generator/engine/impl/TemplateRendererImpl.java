package com.vschwarzer.baasinga.service.generator.engine.impl;

import com.vschwarzer.baasinga.domain.model.common.DomainType;
import com.vschwarzer.baasinga.domain.model.render.Application;
import com.vschwarzer.baasinga.service.common.BaseService;
import com.vschwarzer.baasinga.service.generator.common.Constants;
import com.vschwarzer.baasinga.service.generator.common.DirectoryUtil;
import com.vschwarzer.baasinga.service.generator.config.PathConfig;
import com.vschwarzer.baasinga.service.generator.engine.TemplateRenderer;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent Schwarzer on 19.06.15.
 */
@Service
public class TemplateRendererImpl extends BaseService implements TemplateRenderer {

    @Autowired
    PathConfig pathConfig;

    @Autowired
    DirectoryUtil directoryUtil;

    @Override
    public void renderConfiguration(Application application) throws TemplateException, IOException {
        renderConfigClasses(application);
        renderPom(application);

    }


    private boolean renderConfigClasses(Application application) throws IOException, TemplateException {

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("package", directoryUtil.getRootPackage());
        String appConfigOutputPath = directoryUtil.getApiBaseDir(application) + "/" + Constants.APP_CONFIG_TARGET_FILENAME;
        render(Constants.APP_CONFIG_TEMPLATE, data, appConfigOutputPath);

        data.put("package", directoryUtil.getRootPackage());
        String webInitOutputPath = directoryUtil.getApiBaseDir(application) + "/" + Constants.WEBAPP_INITIALIZER_TARGET_FILENAME;
        render(Constants.WEBAPP_INITIALIZER_TEMPLATE, data, webInitOutputPath);

        Map<String, Object> abstractEntityData = new HashMap<String, Object>();
        abstractEntityData.put("package", directoryUtil.getPackage(DomainType.MODEL));
        String abstractEntityOutputPath = directoryUtil.getModelBaseDir(application) + "/" + Constants.ABSTRACT_ENTITY_TARGET_FILENAME;
        render(Constants.ABSTRACT_ENTITY_TEMPLATE, abstractEntityData, abstractEntityOutputPath);

        return true;
    }

    private boolean renderPom(Application application) throws IOException, TemplateException {

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appName", application.getName());
        data.put("appVersion", application.getVersion().getName());
        data.put("hibernate", Constants.HIBERNATE_VERSION);
        data.put("springframework", Constants.SPRING_FW_VERSION);
        data.put("springdatajpa", Constants.SPRING_DATA_JPA_VERSION);
        data.put("hsqldb", Constants.HSQLDB_VERSION);
        data.put("springdatawebmvc", Constants.SPRING_MVC_VERSION);
        data.put("servlet", Constants.SERVLET_VERSION);
        data.put("slf4j", Constants.SL4J_VERSION);
        data.put("logback", Constants.LOGBACK_VERSION);
        data.put("mavencompiler", Constants.MAVEN_COMPILER_VERSION);
        data.put("jdkversion", Constants.TARGET_JDK_VERSION);
        data.put("warplugin", Constants.MAVEN_WAR_PLUGIN_VERSION);

        String outputPath = directoryUtil.getMavenRootDir(application) + "/" + Constants.POM_TARGET_FILENAME;
        render(Constants.POM_TEMPLATE, data, outputPath);
        return true;
    }

    @Override
    public void render(String template, Map<String, Object> data, String outputPath) throws IOException, TemplateException {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(directoryUtil.getTemplateDir()));
        Template templateConfig = cfg.getTemplate(template);
        Writer file = new FileWriter(new File(outputPath));
        templateConfig.process(data, file);
        file.flush();
        file.close();
    }
}
