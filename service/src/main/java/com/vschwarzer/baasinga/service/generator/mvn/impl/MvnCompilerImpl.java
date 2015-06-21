package com.vschwarzer.baasinga.service.generator.mvn.impl;

import com.vschwarzer.baasinga.service.common.AbstractService;
import com.vschwarzer.baasinga.service.generator.mvn.MavenCompiler;
import org.apache.maven.cli.MavenCli;
import org.springframework.stereotype.Service;

/**
 * Created by Vincent Schwarzer on 19.06.15.
 */
@Service
public class MvnCompilerImpl extends AbstractService implements MavenCompiler {

    private MavenCli mavenCli = new MavenCli();

    @Override
    public int mvn(String path) {
        MavenCli cli = new MavenCli();
        int result = cli.doMain(new String[]{"clean", "install"}, path, System.out, System.err);
        LOG.info("Job ran with code: " + result);
        return result;
    }
}
