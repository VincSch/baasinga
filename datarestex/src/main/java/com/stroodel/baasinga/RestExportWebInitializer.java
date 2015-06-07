package com.stroodel.baasinga;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
@Import(JpaConfig.class)
public class RestExportWebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootCtx = new AnnotationConfigWebApplicationContext();
        servletContext.addListener(new ContextLoaderListener(rootCtx));

        AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
        webCtx.register(RestMvcConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webCtx);
        ServletRegistration.Dynamic reg = servletContext.addServlet("rest-exporter", dispatcherServlet);
        reg.setLoadOnStartup(1);
        reg.addMapping("/*");
    }

}
