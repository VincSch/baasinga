package com.stroodel.baasinga;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

/**
 * Created by Vincent Schwarzer on 07.06.15.
 */
@Configuration
public class RestMvcConfig extends RepositoryRestMvcConfiguration{

//    @Override
//    public RepositoryRestConfiguration config() {
//        RepositoryRestConfiguration repConfig = super.config();
//        config().setBasePath("/restdata");
//        return repConfig;
//    }
}
