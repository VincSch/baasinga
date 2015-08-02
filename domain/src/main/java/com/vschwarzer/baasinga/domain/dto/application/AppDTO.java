package com.vschwarzer.baasinga.domain.dto.application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent Schwarzer on 08.07.15.
 */
public class AppDTO extends BaseDTO {

    private String name = "";
    private String port = "";
    private String version = "";
    private Boolean cloudEnabled = false;
    private Boolean secEnabled = false;
    private String description = "";
    private List<ModelDTO> models = new ArrayList<>();
    public AppDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getCloudEnabled() {
        return cloudEnabled;
    }

    public void setCloudEnabled(Boolean cloudEnabled) {
        this.cloudEnabled = cloudEnabled;
    }

    public Boolean getSecEnabled() {
        return secEnabled;
    }

    public void setSecEnabled(Boolean secEnabled) {
        this.secEnabled = secEnabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ModelDTO> getModels() {
        return models;
    }

    public void setModels(List<ModelDTO> models) {
        this.models = models;
    }
}
