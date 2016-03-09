package com.kimmin.es.plugin.tiny;

import com.kimmin.es.plugin.tiny.impl.ImplScanner;
import com.kimmin.es.plugin.tiny.service.RegisterService;
import com.kimmin.es.plugin.tiny.thread.TimingManager;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.SuppressForbidden;
import org.elasticsearch.common.component.AbstractLifecycleComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;

/**
 * Created by kimmin on 3/8/16.
 */
public class TinyTimerComponent extends AbstractLifecycleComponent<TinyTimerComponent> {

    private final ESLogger logger;
    private static Client client;

    @Inject
    public TinyTimerComponent(Settings settings, Client client){
        super(settings);
        this.logger = Loggers.getLogger("plugin.tiny_monitor",settings);
        TinyTimerComponent.client = client;
    }

    @SuppressForbidden(reason = "Unknown Reason..")
    @Override
    protected void doStart() throws ElasticsearchException {
        System.out.println("Plugin Started..");
        logger.info("Monitor Plugin Starter ..");

        /** Scan tasks in the classpath **/
        ImplScanner.scanImpl();
        RegisterService.latch.countDown();
    }


    @Override
    protected void doStop() throws ElasticsearchException {
        System.out.println("Plugin Start Complete!");
        logger.info("Plugin start complete..");
    }

    @Override
    protected void doClose() throws ElasticsearchException {
        TimingManager.getInstance().shutdown();
    }

    public static Client getClient(){
        return client;
    }

}
