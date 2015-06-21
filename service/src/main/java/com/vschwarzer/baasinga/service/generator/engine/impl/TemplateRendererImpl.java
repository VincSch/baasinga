package com.vschwarzer.baasinga.service.generator.engine.impl;

import com.sun.tools.internal.jxc.ap.Const;
import com.vschwarzer.baasinga.service.common.AbstractService;
import com.vschwarzer.baasinga.service.generator.common.Constants;
import com.vschwarzer.baasinga.service.generator.common.DirectoryUtil;
import com.vschwarzer.baasinga.service.generator.config.PathConfig;
import com.vschwarzer.baasinga.service.generator.engine.TemplateRenderer;
import com.vschwarzer.baasinga.service.generator.model.ApplicationData;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vincent Schwarzer on 19.06.15.
 */
@Service
public class TemplateRendererImpl extends AbstractService implements TemplateRenderer {

    @Autowired
    PathConfig pathConfig;

    @Autowired
    DirectoryUtil directoryUtil;

    @Override
    public void renderClass(String pathToTemplate) {
        LOG.info(pathConfig.templateRootDir);
        //Freemarker configuration object
        Configuration cfg = new Configuration();
        try {

            //cfg.setDirectoryForTemplateLoading(new File("/Users/vs/templates"));
            cfg.setDirectoryForTemplateLoading(new File(pathToTemplate));
            //Load template from source folder
            //File templateFile = new ClassPathResource("entity.ftl").getFile();
            Template template = cfg.getTemplate("entity.ftl");

            // Build the data-model
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("className", "Test");
            data.put("methodName", "Test");

            //List parsing
            List<String> methods = new ArrayList<String>();
            methods.add("System.out.println('Hello');");
            methods.add("System.out.println('World');");

            data.put("methods", methods);


            // Console output
            Writer out = new OutputStreamWriter(System.out);

            template.process(data, out);
            out.flush();

            // File output
            Writer file = new FileWriter(new File("/Users/vs/Desktop/entity.java"));
            template.process(data, file);
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean renderConfigClasses(String pathToTemplate, ApplicationData applicationData) throws IOException, TemplateException {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(pathToTemplate + Constants.TEMPLATE_SUB_DIR));

        Template appConfig = cfg.getTemplate(Constants.APP_CONFIG_CLASSNAME + ".ftl");

        // Build the data-model
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("package", Constants.ROOT_PACKAGE_NAME.replace("/", ".").substring(1));
        // File output
        String outputPath = directoryUtil.getMavenRootDir(applicationData) + Constants.MVN_JAVA_DIR + Constants.ROOT_PACKAGE_NAME;
        Writer file = new FileWriter(new File(outputPath + "/" + Constants.APP_CONFIG_CLASSNAME + ".java"));
        appConfig.process(data, file);
        file.flush();
        file.close();

        Template webConfig = cfg.getTemplate(Constants.WEBAPP_INITIALIZER_CLASSNAME + ".ftl");

        // Build the data-model
        Map<String, Object> data2 = new HashMap<String, Object>();
        data2.put("package", Constants.ROOT_PACKAGE_NAME.replace("/", ".").substring(1));
        // File output
        Writer file2 = new FileWriter(new File(outputPath + "/" + Constants.WEBAPP_INITIALIZER_CLASSNAME + ".java"));
        webConfig.process(data2, file2);
        file2.flush();
        file2.close();

        return true;
    }

    @Override
    public boolean renderPom(String pathToTemplate, ApplicationData applicationData) throws IOException, TemplateException {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(pathToTemplate + Constants.TEMPLATE_SUB_DIR));

        Template appConfig = cfg.getTemplate("pom.ftl");

        // Build the data-model
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("appName", applicationData.getApplicationName());
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


        // File output
        String outputPath = directoryUtil.getMavenRootDir(applicationData);
        Writer file = new FileWriter(new File(outputPath + "/pom.xml"));
        appConfig.process(data, file);
        file.flush();
        file.close();
        return true;
    }
}
