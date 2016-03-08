//package com.kimmin.es.plugin;
//
//import org.elasticsearch.ElasticsearchException;
//import org.elasticsearch.action.ListenableActionFuture;
//import org.elasticsearch.action.admin.indices.get.GetIndexRequestBuilder;
//import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
//import org.elasticsearch.bootstrap.Elasticsearch;
//import org.elasticsearch.client.Client;
//import org.elasticsearch.client.IndicesAdminClient;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.SuppressForbidden;
//import org.elasticsearch.common.component.AbstractLifecycleComponent;
//import org.elasticsearch.common.inject.Inject;
//import org.elasticsearch.common.logging.ESLogger;
//import org.elasticsearch.common.logging.Loggers;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.env.Environment;
//import org.elasticsearch.node.NodeBuilder;
//import org.elasticsearch.transport.TransportService;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.concurrent.CountDownLatch;
//
//import java.nio.file.Files;
//import java.nio.file.Path;
//
///**
// * Created by min.jin on 2016/3/7.
// */
//
//
//public class ControllerSingleton extends AbstractLifecycleComponent<ControllerSingleton> {
//
//    private final Environment environment;
//    private final ESLogger logger;
//    private final Client client;
//
//    public static CountDownLatch latch = new CountDownLatch(1);
//    //private Controller controller;
//
//    @Inject
//    public ControllerSingleton(Settings settings, Environment environment, Client client){
//        super(settings);
//        this.environment = environment;
//        this.logger = Loggers.getLogger("plugin.my_monitor",settings);
//        this.client = client;
//    }
//
//    @SuppressForbidden(reason = "Unknown Reason..")
//    @Override
//    protected void doStart() throws ElasticsearchException{
//        System.out.println("Plugin Started..");
//        logger.info("Monitor Plugin Starter ..");
//
//        /** Prepare a client instance for Status singleton **/
//        MonitorServerStatus.getInstance().setClient(client);
//
//        /** Let Status singleton handle monitor thread **/
//        /** Starting Monitoring Thread **/
//        MonitorServerStatus.getInstance().monitorThread = new MonitorThread();
//        MonitorServerStatus.getInstance().monitorThread.start();
//
//    }
//
//    @Override
//    protected void doStop() throws ElasticsearchException {
//        System.out.println("Monitor Start Complete!");
//        logger.info("Plugin Stopped..");
//        MonitorServerStatus.getInstance().monitorThread.bRun = false;
//    }
//
//    @Override
//    protected void doClose() throws ElasticsearchException {
//        // Noop
//    }
//
//}
