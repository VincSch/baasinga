package com.stroodel.baasinga;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.stroodel")
@Import(InfrastructureConfig.class)
public class JpaConfig {

}
