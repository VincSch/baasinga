package com.vschwarzer.baasinga.service.generator.engine;

import com.vschwarzer.baasinga.service.generator.model.ApplicationData;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * Created by Vincent Schwarzer on 19.06.15.
 */
public interface TemplateRenderer {

    void renderClass(String pathToTemplate);
    boolean renderConfigClasses(String pathToTemplate, ApplicationData applicationData) throws IOException, TemplateException;
    boolean renderPom(String pathToTemplate, ApplicationData applicationData) throws IOException, TemplateException;
}
