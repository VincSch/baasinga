package com.vschwarzer.baasinga.service.generator.common;

/**
 * Created by Vincent Schwarzer on 20.06.15.
 */
public final class Constants {


    /**
     * commom
     */
    public static final String TEMPLATE_FILE_ENDING = ".ftl";
    public static final String JAVA_FILE_ENDING = ".java";


    /**
     * Path's
     */
    public static final String MVN_JAVA_DIR = "/src/main/java";
    public static final String MVN_RESOURCE_DIR = "/src/main/resources";
    public static final String MODEL_PACKAGE_NAME = "/model";
    public static final String REPOSITORY_PACKAGE_NAME = "/repository";
    public static final String ROOT_PACKAGE_NAME = "/com/baasinga/api";
    public static final String TEMPLATE_SUB_DIR = "/render";


    /**
     * Template names
     */
    public static final String APP_CONFIG_TEMPLATE = "/classes/AppConfiguration.ftl";
    public static final String POM_TEMPLATE = "/config/pom.ftl";
    public static final String WEBAPP_INITIALIZER_TEMPLATE = "/classes/WebAppInitializer.ftl";
    public static final String ABSTRACT_ENTITY_TEMPLATE = "/classes/AbstractEntity.ftl";
    public static final String ENTITY_TEMPLATE = "/classes/Entity.ftl";
    public static final String REPOSITORY_TEMPLATE = "/classes/Repository.ftl";

    /**
     * Target file names
     */
    public static final String APP_CONFIG_TARGET_FILENAME = "AppConfiguration.java";
    public static final String WEBAPP_INITIALIZER_TARGET_FILENAME = "WebAppInitializer.java";
    public static final String ABSTRACT_ENTITY_TARGET_FILENAME = "AbstractEntity.java";
    public static final String LOG4J_PROPERTIES = "/config/log4j.properties";
    public static final String POM_TARGET_FILENAME = "pom.xml";

    /**
     * POM Versions
     */
    public static final String SPRING_FW_VERSION = "4.1.6.RELEASE";
    public static final String SPRING_MVC_VERSION = "2.3.0.RELEASE";
    public static final String SPRING_DATA_JPA_VERSION = "1.8.0.RELEASE";
    public static final String PROJECT_ENCODING = "UTF8";
    public static final String HIBERNATE_VERSION = "4.3.7.Final";
    public static final String HSQLDB_VERSION = "2.3.2";
    public static final String SL4J_VERSION = "1.7.12";
    public static final String SERVLET_VERSION = "3.1.0";
    public static final String LOGBACK_VERSION = "1.1.3";
    public static final String MAVEN_COMPILER_VERSION = "3.1";
    public static final String MAVEN_WAR_PLUGIN_VERSION = "2.6";
    public static final String TARGET_JDK_VERSION = "1.8";

}
