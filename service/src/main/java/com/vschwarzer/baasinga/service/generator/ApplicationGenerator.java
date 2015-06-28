package com.vschwarzer.baasinga.service.generator;

import com.vschwarzer.baasinga.domain.model.render.Application;

/**
 * Created by Vincent Schwarzer on 20.06.15.
 */
public interface ApplicationGenerator {

    void generateApplication(Application application);

    void compileApplication(Application application);
}
