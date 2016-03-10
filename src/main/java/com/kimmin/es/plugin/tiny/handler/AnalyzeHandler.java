package com.kimmin.es.plugin.tiny.handler;

import com.kimmin.es.plugin.tiny.service.AnalyzeService;
import com.kimmin.es.plugin.tiny.service.RegisterService;
import com.kimmin.es.plugin.tiny.task.CycleTimingTask;
import com.kimmin.es.plugin.tiny.util.ManyUtil;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.rest.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by kimmin on 3/9/16.
 */

public class AnalyzeHandler implements RestHandler {

    @Inject
    public AnalyzeHandler(RestController controller){
        controller.registerHandler(RestRequest.Method.GET,"/tiny/analyze",this);
    }

    public void handleRequest(final RestRequest restRequest, final RestChannel channel){
        AnalyzeService.getInstance().analyzeLogQueue();
        try{
            channel.sendResponse(new BytesRestResponse(RestStatus.OK,"image/png",ManyUtil.mapFileToByteArray(ManyUtil.TMP_PNG_FILE_NAME)));
        }catch (IOException ioe){
            ioe.printStackTrace();
            channel.sendResponse(new BytesRestResponse(RestStatus.OK, "Retrieving File Failure!"));
        }

    }

}
