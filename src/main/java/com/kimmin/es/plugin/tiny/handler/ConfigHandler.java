package com.kimmin.es.plugin.tiny.handler;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.rest.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kimmin on 3/8/16.
 */

public class ConfigHandler implements RestHandler{

    @Inject
    public ConfigHandler(RestController controller){
        controller.registerHandler(RestRequest.Method.GET,"/_plugin/tiny/disable",this);
    }

    public void handleRequest(final RestRequest restRequest, final RestChannel channel){
//        restRequest
        channel.sendResponse(new BytesRestResponse(RestStatus.OK, "DIS"));
    }

}
