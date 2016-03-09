package com.kimmin.es.plugin.tiny.handler;

import com.kimmin.es.plugin.tiny.exception.NoSuchTaskException;
import com.kimmin.es.plugin.tiny.service.RegisterService;
import com.kimmin.es.plugin.tiny.thread.TimingManager;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.rest.*;

/**
 * Created by kimmin on 3/9/16.
 */
public class EnableTaskHandler implements RestHandler{


    @Inject
    public EnableTaskHandler(RestController controller){
        controller.registerHandler(RestRequest.Method.GET,"/tiny/enable_task",this);
    }

    public void handleRequest(final RestRequest restRequest, final RestChannel channel){
        if(restRequest.hasParam("task")){
            Boolean enable = RegisterService.getInstance().getTaskStatusByName(restRequest.param("task"));
            if(enable != null){
                try{
                    /** Enable & Start **/
                    RegisterService.getInstance().enableTask(restRequest.param("task"));
                    TimingManager.getInstance().startTask(restRequest.param("task"));
                }catch (NoSuchTaskException e){
                    e.printStackTrace();
                    channel.sendResponse(new BytesRestResponse(RestStatus.OK, "NO SUCH TASK!"));
                }
            }else{
                channel.sendResponse(new BytesRestResponse(RestStatus.OK, "NO SUCH TASK!"));
            }
        }else {
            channel.sendResponse(new BytesRestResponse(RestStatus.OK, "Attach Your Task-name!"));
        }
    }

}
