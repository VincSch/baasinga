package com.vschwarzer.baasinga.web.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by vs on 23.05.15.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.vschwarzer")
@PropertySource(value = "classpath:application.properties")
@Import({WebInitializer.class, DispatcherConfig.class})
public class AppConfig {

    /**
     * Ensures that placeholders are replaced with property values
     */
    @Bean
    static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
