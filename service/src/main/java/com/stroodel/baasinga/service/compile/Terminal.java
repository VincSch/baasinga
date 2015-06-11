package com.stroodel.baasinga.service.compile;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by vs on 11.06.15.
 */
@Service
public class Terminal {
    private final Logger LOG = LoggerFactory.getLogger(Terminal.class);

    public void mvnCleanInstall(String path){
        String gitClone =  "git clone git@vschwarzer.com:/home/git/baasinga.git";
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("ls");
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line = "";
        try {
            while ((line = reader.readLine())!= null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
