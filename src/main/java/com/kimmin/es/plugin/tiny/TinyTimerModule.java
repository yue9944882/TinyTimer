package com.kimmin.es.plugin.tiny;

import com.kimmin.es.plugin.tiny.handler.DisableConfigHandler;
import com.kimmin.es.plugin.tiny.handler.EnableConfigHandler;
import com.kimmin.es.plugin.tiny.handler.MailConfigHandler;
import com.kimmin.es.plugin.tiny.handler.SelfPressureHandler;
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
        bind(EnableConfigHandler.class).asEagerSingleton();
        bind(DisableConfigHandler.class).asEagerSingleton();
        bind(MailConfigHandler.class).asEagerSingleton();
        bind(SelfPressureHandler.class).asEagerSingleton();
    }

}
