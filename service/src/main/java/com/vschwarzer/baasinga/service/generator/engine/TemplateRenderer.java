package com.vschwarzer.baasinga.service.generator.engine;

import com.vschwarzer.baasinga.domain.model.render.Application;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Vincent Schwarzer on 19.06.15.
 */
public interface TemplateRenderer {

    void renderClass(String pathToTemplate);

    /**
     * Add configuration objects like pom and WebInit classes
     *
     * @param application Application
     */
    void renderConfiguration(Application application) throws TemplateException, IOException;

    /**
     * Render a given template with data and output directory
     *
     * @param template   template to render
     * @param data       data to fill in the template
     * @param outputPath store location after rendering
     * @throws IOException       file not found
     * @throws TemplateException template not found
     */
    void render(String template, Map<String, Object> data, String outputPath) throws IOException, TemplateException;
}
