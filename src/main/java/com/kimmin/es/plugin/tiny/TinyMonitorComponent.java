package com.kimmin.es.plugin.tiny;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.SuppressForbidden;
import org.elasticsearch.common.component.AbstractLifecycleComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;

import java.util.concurrent.CountDownLatch;

/**
 * Created by kimmin on 3/8/16.
 */
public class TinyMonitorComponent extends AbstractLifecycleComponent<TinyMonitorComponent> {

    private final ESLogger logger;
    private final Client client;

    @Inject
    public TinyMonitorComponent(Settings settings, Client client){
        super(settings);
        this.logger = Loggers.getLogger("plugin.my_monitor",settings);
        this.client = client;
    }

    @SuppressForbidden(reason = "Unknown Reason..")
    @Override
    protected void doStart() throws ElasticsearchException {
        System.out.println("Plugin Started..");
        logger.info("Monitor Plugin Starter ..");
    }


    @Override
    protected void doStop() throws ElasticsearchException {
        System.out.println("Monitor Start Complete!");
        logger.info("Plugin Stopped..");

    }

    @Override
    protected void doClose() throws ElasticsearchException {

    }

}
