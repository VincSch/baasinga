package com.vschwarzer.baasinga.service.generator.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Vincent Schwarzer on 19.06.15.
 */
@Component
public class PathConfig {

    @Value("${config.templateRootDir}")
    public String templateRootDir;

    @Value("${config.outputDir}")
    public String outputDir;
}
