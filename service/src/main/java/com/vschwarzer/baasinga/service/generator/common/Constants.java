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
    public static final String APP_CONFIG_TEMPLATE = "/classes/Application.ftl";
    public static final String POM_TEMPLATE = "/config/pom.ftl";
    public static final String SECURITY_CONFIG_TEMPLATE = "/classes/SecurityConfiguration.ftl";
    public static final String ABSTRACT_ENTITY_TEMPLATE = "/classes/AbstractEntity.ftl";
    public static final String ENTITY_TEMPLATE = "/classes/Entity.ftl";
    public static final String REPOSITORY_TEMPLATE = "/classes/Repository.ftl";

    /**
     * Target file names
     */
    public static final String APP_CONFIG_TARGET_FILENAME = "Application.java";
    public static final String SECURITY_CONFIG_TARGET_FILENAME = "SecurityConfiguration.java";
    public static final String ABSTRACT_ENTITY_TARGET_FILENAME = "AbstractEntity.java";
    public static final String LOG4J_PROPERTIES = "/config/log4j.properties";
    public static final String APPLICATION_PROPERTIES = "/config/application.properties";
    public static final String POM_TARGET_FILENAME = "pom.xml";

    /**
     * POM Versions
     */
    public static final String SPRING_SEC_VERSION = "4.0.2.RELEASE";
    public static final String SPRING_BOOT_STARTER_PARENT = "1.2.5.RELEASE"; //"1.3.0.M2";
    public static final String H2_DB_VERSION = "1.4.187";
    public static final String MAVEN_SPRING_BOOT_PLUGIN_VERSION = "1.2.5.RELEASE";
    public static final String MAVEN_COMPILER_VERSION = "3.1";
    public static final String TARGET_JDK_VERSION = "1.8";
    public static final String PACKAGING_TYPE = "jar";

}
