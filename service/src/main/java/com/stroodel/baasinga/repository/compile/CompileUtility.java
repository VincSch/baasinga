package com.stroodel.baasinga.repository.compile;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by vs on 25.05.15.
 */
@Service
public class CompileUtility {

    public boolean compile(){
// Prepare source somehow.
        String source = "package test; public class Test { static { System.out.println(\"hello\"); } public Test() { System.out.println(\"world\"); } }";

// Save source in .java file.
        File folder = null;
        folder = new FileSystemResource("/Users/vs/codee").getFile();

        File[] listOfFiles = folder.listFiles();
        File root = new File(folder.getPath()); // On Windows running on C:\, this is C:\java.
        File sourceFile = new File(root, "test/Test.java");
        sourceFile.getParentFile().mkdirs();
        try {
            new FileWriter(sourceFile).append(source).close();
        } catch (IOException e) {
            e.printStackTrace();
        }

// Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());

// Load and instantiate compiled class.
        URLClassLoader classLoader = null;
        try {
            classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Class<?> cls = null; // Should print "hello".
        try {
            cls = Class.forName("test.Test", true, classLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object instance = null; // Should print "world".
        try {
            instance = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(instance); // Should print "test.Test@hashcode".

        return true;
    }
}
