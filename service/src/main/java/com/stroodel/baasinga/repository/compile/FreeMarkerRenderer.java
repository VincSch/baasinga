package com.stroodel.baasinga.repository.compile;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vs on 18.06.15.
 */
@Service
public class FreeMarkerRenderer {

    public void renderClass(String pathToTemplate){
        //Freemarker configuration object
        Configuration cfg = new Configuration();
        try {

            //cfg.setDirectoryForTemplateLoading(new File("/Users/vs/templates"));
            cfg.setDirectoryForTemplateLoading(new File(pathToTemplate));
            //Load template from source folder
            //File templateFile = new ClassPathResource("entity.ftl").getFile();
            Template template = cfg.getTemplate("entity.ftl");

            // Build the data-model
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("className", "Test");
            data.put("methodName", "Test");

            //List parsing
            List<String> methods = new ArrayList<String>();
            methods.add("System.out.println('Hello');");
            methods.add("System.out.println('World');");

            data.put("methods", methods);


            // Console output
            Writer out = new OutputStreamWriter(System.out);

            template.process(data, out);
            out.flush();

            // File output
            Writer file = new FileWriter(new File("/Users/vs/Desktop/entity.java"));
            template.process(data, file);
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
