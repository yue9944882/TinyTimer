package com.kimmin.es.plugin.tiny.handler;

import com.kimmin.es.plugin.tiny.util.ManyUtil;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.rest.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kimmin on 3/8/16.
 */

public class DisableConfigHandler implements RestHandler{

    @Inject
    public DisableConfigHandler(RestController controller){
        controller.registerHandler(RestRequest.Method.GET,"/tiny/disable",this);
    }

    public void handleRequest(final RestRequest restRequest, final RestChannel channel) throws IOException{

    }

}
