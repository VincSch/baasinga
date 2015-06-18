package com.stroodel.baasinga.repository.compile;

import org.apache.maven.cli.MavenCli;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by vs on 11.06.15.
 */
@Service
public class Terminal {
    private final Logger LOG = LoggerFactory.getLogger(Terminal.class);

    public void mvnCleanInstall(String path){
        String gitClone =  "git clone git@vschwarzer.com:/home/git/baasinga.git";
        String [] cmds = {"mvn clean install"};
        String [] env = {"MVN_HOME"};
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(cmds, env, new File(path));
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
                LOG.info(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mvn(String path) {
        MavenCli cli = new MavenCli();
        int result = cli.doMain(new String[]{"clean", "install", "-l /Users/vs/build.txt", "-X", "-V"}, path, System.out, System.err);
        LOG.info("Job ran with code: " + result);
    }
}
