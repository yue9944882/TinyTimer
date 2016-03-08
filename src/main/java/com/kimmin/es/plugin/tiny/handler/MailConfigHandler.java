package com.kimmin.es.plugin.tiny.handler;

import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.rest.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by kimmin on 3/8/16.
 */
public class MailConfigHandler implements RestHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    public MailConfigHandler(RestController controller){
        controller.registerHandler(RestRequest.Method.POST,"/_plugin/tiny/mail",this);
    }

    public void handleRequest(final RestRequest restRequest, final RestChannel channel){
        try{
            Map map = objectMapper.readValue(restRequest.content().toBytes(),Map.class);
            String smtpHost = (String) map.get("host.smtp");
            String username = (String) map.get("host.name");
            String password = (String) map.get("host.password");
            channel.sendResponse(new BytesRestResponse(RestStatus.OK, "CONFIG SUCCESS!"));
        }catch (IOException ioe){
            ioe.fillInStackTrace();
        }

    }

}
