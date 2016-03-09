package com.kimmin.es.plugin.tiny.handler;

import com.kimmin.es.plugin.tiny.exception.NoSuchTaskException;
import com.kimmin.es.plugin.tiny.service.RegisterService;
import com.kimmin.es.plugin.tiny.thread.TimingManager;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.rest.*;

/**
 * Created by kimmin on 3/8/16.
 */
public class EnableConfigHandler implements RestHandler{

    @Inject
    public EnableConfigHandler(RestController controller){
        controller.registerHandler(RestRequest.Method.GET,"/tiny/enable",this);
    }

    public void handleRequest(final RestRequest restRequest, final RestChannel channel) {
        if (TimingManager.getInstance().isStarted()) {
            /** Do nothing here **/
            channel.sendResponse(new BytesRestResponse(RestStatus.OK, "Invalid: Service Still Running"));
        } else {
            TimingManager.getInstance().start();
            channel.sendResponse(new BytesRestResponse(RestStatus.OK, "Successfully started service"));
        }
    }
}
