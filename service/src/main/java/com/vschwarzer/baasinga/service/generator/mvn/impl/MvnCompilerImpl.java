package com.vschwarzer.baasinga.service.generator.mvn.impl;

import com.vschwarzer.baasinga.service.common.BaseService;
import com.vschwarzer.baasinga.service.generator.mvn.MavenCompiler;
import org.apache.maven.cli.MavenCli;
import org.springframework.stereotype.Service;

/**
 * Created by Vincent Schwarzer on 19.06.15.
 */
@Service
public class MvnCompilerImpl extends BaseService implements MavenCompiler {

    private MavenCli cli = new MavenCli();

    @Override
    public int cleanInstall(String path) {
        int result = cli.doMain(new String[]{"clean", "install"}, path, System.out, System.err);
        LOG.info("Maven clean install finished with code: " + result);
        return result;
    }
}
