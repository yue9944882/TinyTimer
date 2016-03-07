package com.kimmin.es.plugin;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.bootstrap.Elasticsearch;
import org.elasticsearch.common.SuppressForbidden;
import org.elasticsearch.common.component.AbstractLifecycleComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;

import java.util.concurrent.CountDownLatch;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by min.jin on 2016/3/7.
 */


public class ControllerSingleton extends AbstractLifecycleComponent<ControllerSingleton> {

    private final Environment environment;
    private final ESLogger logger;

    public static CountDownLatch latch = new CountDownLatch(1);
    //private Controller controller;

    @Inject
    public ControllerSingleton(Settings settings, Environment environment){
        super(settings);
        this.environment = environment;
        this.logger = Loggers.getLogger("plugin.my_monitor",settings);
    }

    @SuppressForbidden(reason = "Unknown Reason..")
    @Override
    protected void doStart() throws ElasticsearchException{
        System.out.println("Plugin Started..");
        logger.info("Plugin Started..");
        /** Starting Monitoring Thread **/

        /** Strap & Wait until monitor expire **/
        try{
            latch.await();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }

    @Override
    protected void doStop() throws ElasticsearchException {
        System.out.println("Plugin Stopped..");
        logger.info("Plugin Stopped..");


    }

    @Override
    protected void doClose() throws ElasticsearchException {
        // Noop
    }

}
