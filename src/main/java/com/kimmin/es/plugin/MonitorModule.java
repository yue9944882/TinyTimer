package com.kimmin.es.plugin;

import org.elasticsearch.common.inject.AbstractModule;

/**
 * Created by min.jin on 2016/3/7.
 */
public class MonitorModule extends AbstractModule{

    @Override
    protected void configure(){
        bind(ControllerSingleton.class).asEagerSingleton();
    }

}
