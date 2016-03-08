package com.kimmin.es.plugin;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.admin.indices.get.GetIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.bootstrap.Elasticsearch;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.SuppressForbidden;
import org.elasticsearch.common.component.AbstractLifecycleComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.env.Environment;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.transport.TransportService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by min.jin on 2016/3/7.
 */


public class ControllerSingleton extends AbstractLifecycleComponent<ControllerSingleton> {

    private final Environment environment;
    private final ESLogger logger;
    private final Client client;

    public static CountDownLatch latch = new CountDownLatch(1);
    //private Controller controller;

    @Inject
    public ControllerSingleton(Settings settings, Environment environment, Client client){
        super(settings);
        this.environment = environment;
        this.logger = Loggers.getLogger("plugin.my_monitor",settings);
        this.client = client;
    }

    @SuppressForbidden(reason = "Unknown Reason..")
    @Override
    protected void doStart() throws ElasticsearchException{
        System.out.println("Plugin Started..");
        logger.info("Plugin Started..");

        MonitorServerStatus.getInstance().setClient(client);

//        IndicesAdminClient admin = client.admin().indices();
//        GetIndexRequestBuilder builder = admin.prepareGetIndex();
//        ListenableActionFuture<GetIndexResponse> future = builder.execute();
//        GetIndexResponse response = future.actionGet();
//        String[] names = response.getIndices();///Index Names
//        for(String name: names){
//            System.out.println(name);
//        }

        /** Starting Monitoring Thread **/
        MonitorThread monitorThread = new MonitorThread();
        monitorThread.start();
//        /** Strap & Wait until monitor expire **/
//        try{
//            latch.await();
//        }catch (InterruptedException ie){
//            ie.printStackTrace();
//        }

        GetIndexResponse response2 = client.admin().indices().prepareGetIndex()
                .execute().actionGet();
        String[] names2 = response2.getIndices();///Index Names
        for(String name: names2){
            System.out.println(name);
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
