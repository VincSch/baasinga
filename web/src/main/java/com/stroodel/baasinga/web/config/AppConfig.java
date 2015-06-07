package com.stroodel.baasinga.web.config;

import com.stroodel.baasinga.RestMvcConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by vs on 23.05.15.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.stroodel")
@Import({ WebInitializer.class, DispatcherConfig.class})
public class AppConfig {
}
