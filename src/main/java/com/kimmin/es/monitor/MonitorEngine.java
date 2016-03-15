package com.kimmin.es.monitor;

import com.kimmin.es.plugin.tiny.var.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by min.jin on 2016/3/15.
 */


public class MonitorEngine {

    private final String ipAddr;
    private final long interval;
    private final int port;

    private ObjectMapper om = new ObjectMapper();

    public MonitorEngine(String ipAddr, int port, long interval){
        this.interval = interval;
        this.ipAddr = ipAddr;
        this.port = port;
    }

    public void dumpStatsToCsv(File csvFile){
        /** Check file format **/
        if(!csvFile.getName().endsWith(".csv")){
            System.err.println("Snapshots must be dumped to CSV formatted file!");
        }


    }



    public String[] snapshotAndMapJVMToStrings() throws IOException{
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:" + port + "/_stats?pretty");
        httpGet.addHeader("accept","application/json");
        try{
            HttpResponse response = client.execute(httpGet);
            HttpEntity respEntity = response.getEntity();
            if(response.getStatusLine().getStatusCode() == 200){
                Map map = om.readValue(respEntity.getContent(), Map.class);
                Iterator iter = ((Map)map.get("nodes")).entrySet().iterator();
                while(iter.hasNext()){
                    Map node = (Map)iter.next();
                    
                }
            }
    }

}
