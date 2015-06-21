package com.vschwarzer.baasinga.service.generator.model;


import org.springframework.stereotype.Component;

/**
 * Created by Vincent Schwarzer on 20.06.15.
 */
@Component
public class ApplicationData {

    private String applicationName;
    private String pathToMavenProject;

    public String getPathToMavenProject() {
        return pathToMavenProject;
    }

    public void setPathToMavenProject(String pathToMavenProject) {
        this.pathToMavenProject = pathToMavenProject;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
