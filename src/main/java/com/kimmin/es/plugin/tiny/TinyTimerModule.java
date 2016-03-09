package com.kimmin.es.plugin.tiny;

import com.kimmin.es.plugin.tiny.handler.DisableConfigHandler;
import org.elasticsearch.common.inject.AbstractModule;

/**
 * Created by kimmin on 3/8/16.
 */
public class TinyTimerModule extends AbstractModule {

    @Override
    protected void configure(){
        /** Bind Component **/
        bind(TinyTimerComponent.class).asEagerSingleton();

        /** Bind RESTful handler **/
        bind(DisableConfigHandler.class).asEagerSingleton();
    }

}
