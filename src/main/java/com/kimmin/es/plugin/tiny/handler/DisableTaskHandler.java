package com.kimmin.es.plugin.tiny.handler;

import com.kimmin.es.plugin.tiny.exception.NoSuchTaskException;
import com.kimmin.es.plugin.tiny.service.RegisterService;
import com.kimmin.es.plugin.tiny.thread.TimingManager;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.rest.*;

/**
 * Created by kimmin on 3/9/16.
 */
public class DisableTaskHandler implements RestHandler {

    @Inject
    public DisableTaskHandler(RestController controller){
        controller.registerHandler(RestRequest.Method.GET,"/tiny/disable_task",this);
    }

    public void handleRequest(final RestRequest restRequest, final RestChannel channel){
        TimingManager.getInstance().shutdown();
    }

}
