package com.kimmin.es.plugin.tiny.handler;

import com.kimmin.es.plugin.tiny.TinyTimerComponent;
import com.kimmin.es.plugin.tiny.service.RegisterService;
import com.kimmin.es.plugin.tiny.task.CycleTimingTask;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.rest.*;

import java.util.Map;

/**
 * Created by kimmin on 3/9/16.
 */
public class ListTaskHandler implements RestHandler{

    @Inject
    public ListTaskHandler(RestController controller){
        controller.registerHandler(RestRequest.Method.GET,"/tiny/list",this);
    }

    public void handleRequest(final RestRequest restRequest, final RestChannel channel){
        try{
            RegisterService.latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        /** List all the tasks & their status **/
        /** All the task by scanner is default to be enabled **/
        Map<String, CycleTimingTask> taskMap = RegisterService.getInstance().taskMap();
        Map<String, Boolean> statusMap = RegisterService.getInstance().statusMap();

        String content = "";
        content += "Task     \t\t\t\tStatus\n";

        for(Object obj: taskMap.keySet().toArray()) {
            String key = (String) obj;
            content = content + key + "\t\t\t\t" + (statusMap.get(key).booleanValue()?"Y":"N") + "\n";
        }

        channel.sendResponse(new BytesRestResponse(RestStatus.OK, content));
    }


}
