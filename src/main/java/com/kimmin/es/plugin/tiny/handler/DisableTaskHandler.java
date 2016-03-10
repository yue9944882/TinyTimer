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
        if(restRequest.hasParam("task")){
            Boolean enable = RegisterService.getInstance().getTaskStatusByName(restRequest.param("task"));
            if(enable != null){
                RegisterService.getInstance().disableTask(restRequest.param("task"));
                channel.sendResponse(new BytesRestResponse(RestStatus.OK, "Task Disable Success!"));
            }else{
                channel.sendResponse(new BytesRestResponse(RestStatus.OK, "NO SUCH TASK!"));
            }
        }else {
            channel.sendResponse(new BytesRestResponse(RestStatus.OK, "Attach Your Task-name!"));
        }
    }

}
