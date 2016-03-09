package com.kimmin.es.plugin.tiny.handler;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.rest.*;

/**
 * Created by kimmin on 3/8/16.
 */
public class PingHandler implements RestHandler{

    @Inject
    public PingHandler(RestController controller){
        controller.registerHandler(RestRequest.Method.GET,"/_plugin/tiny/ping",this);
    }

    public void handleRequest(final RestRequest restRequest, final RestChannel channel){
        channel.sendResponse(new BytesRestResponse(RestStatus.OK, "FAKE OK"));
    }


}
