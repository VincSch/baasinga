package ${package};

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

@Override
protected Class<?>[] getRootConfigClasses() {
return new Class<?>[]{AppConfiguration.class};
}

@Override
protected Class<?>[] getServletConfigClasses() {
return new Class[]{RepositoryRestMvcConfiguration.class };
}

@Override
protected String[] getServletMappings() {
return new String[]{"/"};
}


}