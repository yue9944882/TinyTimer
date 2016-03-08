package com.kimmin.es.plugin;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.rest.*;

/**
 * Created by min.jin on 2016/3/8.
 */
public class FakeRestHandler implements RestHandler{

    @Inject
    public FakeRestHandler(RestController controller){
        controller.registerHandler(RestRequest.Method.GET,"/_monitor",this);
    }

    public void handleRequest(final RestRequest restRequest, final RestChannel channel){
        channel.sendResponse(new BytesRestResponse(RestStatus.OK,"FAKE!!"));
    }

}
