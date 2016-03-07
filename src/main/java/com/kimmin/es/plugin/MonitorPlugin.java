package com.kimmin.es.plugin;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.component.LifecycleComponent;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugins.Plugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by min.jin on 2016/3/7.
 */
public class MonitorPlugin extends Plugin{

    public static final String DEFAULT_ENABLED_PROPERTY_NAME = "my_monitor.enabled";
    public static final String PLUGIN_NAME = "elasticsearch-my-monitor";
    public static final String DEFAULT_SUITE_PROPERTY_NAME = "suite";
    public static final String DEFAULT_RESOURCES_PROPERTY_NAME = "resources";
    public static final String DEFAULT_COMPONENT_SIZE_PROPERTY_NAME = "controller.pool-size";

    private final boolean tranportClient;
    private final boolean pluginEnabled;
    private final ESLogger logger;

    public MonitorPlugin(Settings settings){
        this.pluginEnabled = settings.getAsBoolean(DEFAULT_ENABLED_PROPERTY_NAME,true);
        this.logger = Loggers.getLogger("plugin.my_monitor",settings);
        this.tranportClient = TransportClient.CLIENT_TYPE.equals(Client.CLIENT_TYPE_SETTING);
    }

    @Override
    public String name(){
        return PLUGIN_NAME;
    }

    @Override
    public String description(){
        return "Monitoring Elasticsearch Servers' Status..";
    }

    @Override
    public Collection<Module> nodeModules(){
        if(pluginEnabled && !tranportClient){
            return Arrays.<Module>asList(new MonitorModule());
        }else{
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Collection<Class<? extends LifecycleComponent>> nodeServices(){
        if(pluginEnabled){
            if(!tranportClient){
                return Arrays.<Class<? extends LifecycleComponent>>asList(ControllerSingleton.class);
            }
        }else{
            logger.info("Plugin Disabled.",name());
        }
        return Collections.emptyList();
    }


}
