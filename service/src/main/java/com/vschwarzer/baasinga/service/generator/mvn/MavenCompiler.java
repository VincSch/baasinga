package com.vschwarzer.baasinga.service.generator.mvn;

/**
 * Created by Vincent Schwarzer on 19.06.15.
 */
public interface MavenCompiler {

    /**
     * @param path
     * @return
     */
    int cleanInstall(String path);
}
