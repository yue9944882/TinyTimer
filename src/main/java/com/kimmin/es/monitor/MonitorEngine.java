package com.kimmin.es.monitor;

import com.kimmin.es.plugin.tiny.var.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.search.aggregations.metrics.percentiles.hdr.InternalHDRPercentileRanks;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by min.jin on 2016/3/15.
 */


public class MonitorEngine {

    private final String ipAddr;
    private final int port;

    private ObjectMapper om = new ObjectMapper();

    /** Delta **/
    long delta_young_count = 0;
    long delta_old_count = 0;
    long delta_young_milli = 0;
    long delta_old_milli = 0;

    long delta_rx_bytes = 0;
    long delta_tx_bytes = 0;
    long delta_index_time = 0;
    long delta_delete_time = 0;
    long delta_merge_time = 0;
    long delta_query_time = 0;


    public MonitorEngine(String ipAddr, int port){

        this.ipAddr = ipAddr;
        this.port = port;
    }

    public void dumpStatsToCsv(File csvFile, long intervalSecond, long count){
        /** Check file format **/
        if(!csvFile.getName().endsWith(".csv")){
            System.err.println("Snapshots must be dumped to CSV formatted file!");
        }
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(csvFile);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            long nowCount = 0;
            long lastTime = new Date().getTime() / 1000;
            while(nowCount < count){
                long nowTime = new Date().getTime() / 1000;
                if(lastTime + intervalSecond <= nowTime){
                    List<String> jvmList = snapshotAndMapJVMToStrings();
                    List<String> crudList = snapshotAndMapCRUDToStrings();
                    ListIterator<String> jvmIter = jvmList.listIterator();
                    ListIterator<String> crudIter = crudList.listIterator();
                    StringBuffer buffer = new StringBuffer();
                    while(jvmIter.hasNext()){
                        buffer.append(jvmIter.next() + ",");
                    }
                    while(crudIter.hasNext()){
                        buffer.append(crudIter.next() + ",");
                    }
                    buffer.deleteCharAt(buffer.lastIndexOf(","));
                    synchronized (MonitorEngine.class){
                        printWriter.println(buffer);
                    }
                    nowCount++;
                    lastTime = nowTime;
                }
            }
            printWriter.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }


    }



    public List<String> snapshotAndMapJVMToStrings(){
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://" + ipAddr + ":" + port + "/_nodes/stats");
            httpGet.addHeader("accept","application/json");
            /**
             * List Format :
             * 1. heap_used_in_bytes
             * 2. used_in_bytes(young)
             * 3. used_in_bytes(survivor)
             * 4. used_in_bytes(old)
             * 5. YGC
             * 6. YGCT
             * 7. OGC
             * 8. OGCT
             * **/
            List<String> list = new ArrayList<String>();

            HttpResponse response = client.execute(httpGet);
            HttpEntity respEntity = response.getEntity();

            if (response.getStatusLine().getStatusCode() == 200) {
                Map map = om.readValue(respEntity.getContent(), Map.class);
                Iterator iter = ((Map) map.get("nodes")).entrySet().iterator();
                while (iter.hasNext()) {
                    Map node = (Map)((Map.Entry) iter.next()).getValue();
                    Map jvm = (Map) node.get("jvm");
                    Map mem = (Map) jvm.get("mem");
                    list.add(mem.get("heap_used_in_bytes").toString());
                    Map pools = (Map) mem.get("pools");
                    Map young = (Map) pools.get("young");
                    list.add(young.get("used_in_bytes").toString());
                    Map survivor = (Map) pools.get("survivor");
                    list.add(survivor.get("used_in_bytes").toString());
                    Map old = (Map) pools.get("old");
                    list.add(old.get("used_in_bytes").toString());
                    Map gc = (Map) jvm.get("gc");
                    Map collectors = (Map) gc.get("collectors");
                    Map youngc = (Map) collectors.get("young");
                    Map oldc = (Map) collectors.get("old");
                    /** Just ignore it Integer -> String -> Long **/
                    long delta_ygc = Long.parseLong(youngc.get("collection_count").toString()) - delta_young_count;
                    delta_young_count = Long.parseLong(youngc.get("collection_count").toString());
                    long delta_ygct = Long.parseLong(youngc.get("collection_time_in_millis").toString()) - delta_young_milli;
                    delta_young_milli = Long.parseLong(youngc.get("collection_time_in_millis").toString());
                    long delta_ogc = Long.parseLong(oldc.get("collection_count").toString()) - delta_old_count;
                    delta_old_count = Long.parseLong(oldc.get("collection_count").toString());
                    long delta_ogct = Long.parseLong(oldc.get("collection_time_in_millis").toString()) - delta_old_milli;
                    delta_old_milli = Long.parseLong(oldc.get("collection_time_in_millis").toString());
                    list.add(""+delta_ygc);
                    list.add(""+delta_ygct);
                    list.add(""+delta_ogc);
                    list.add(""+delta_ogct);
                }
            }
            return list;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<String> snapshotAndMapCRUDToStrings(){
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://" + ipAddr + ":" + port + "/_nodes/stats");
            httpGet.addHeader("accept","application/json");

            /**
             * List Format :
             * 1. rx_size_in_bytes
             * 2. tx_size_in_bytes
             * 3. index_time_in_millis
             * 4. delete_time_in_millis
             * 5. merge_time_in_millis
             * 6. query_time_in_millis
             * 7. avg_load
             * **/

            List<String> list = new ArrayList<String>();

            HttpResponse response = client.execute(httpGet);
            HttpEntity respEntity = response.getEntity();

            if (response.getStatusLine().getStatusCode() == 200) {
                Map map = om.readValue(respEntity.getContent(), Map.class);
                Iterator iter = ((Map) map.get("nodes")).entrySet().iterator();
                while (iter.hasNext()) {
                    Map node = (Map)((Map.Entry) iter.next()).getValue();
                    Map indices = (Map) node.get("indices");
                    Map transport = (Map) node.get("transport");
                    Map indexing = (Map) indices.get("indexing");
                    Map search = (Map) indices.get("search");
                    Map merges = (Map) indices.get("merges");
                    Map os = (Map) node.get("os");
                    Long index_time_in_millis = Long.parseLong(indexing.get("index_time_in_millis").toString()) - delta_index_time;
                    delta_index_time = Long.parseLong(indexing.get("index_time_in_millis").toString());
                    Long delete_time_in_millis = Long.parseLong(indexing.get("delete_time_in_millis").toString()) - delta_delete_time;
                    delta_delete_time = Long.parseLong(indexing.get("delete_time_in_millis").toString());
                    Long merge_time_in_millis = Long.parseLong(merges.get("total_time_in_millis").toString()) - delta_merge_time;
                    delta_merge_time = Long.parseLong(merges.get("total_time_in_millis").toString());
                    Long query_time_in_millis = Long.parseLong(search.get("query_time_in_millis").toString()) - delta_query_time;
                    delta_query_time = Long.parseLong(search.get("query_time_in_millis").toString());
                    Long rx_size_in_bytes = Long.parseLong(transport.get("rx_size_in_bytes").toString()) - delta_rx_bytes;
                    delta_rx_bytes = Long.parseLong(transport.get("rx_size_in_bytes").toString());
                    Long tx_size_in_bytes = Long.parseLong(transport.get("tx_size_in_bytes").toString()) - delta_tx_bytes;
                    delta_tx_bytes = Long.parseLong(transport.get("tx_size_in_bytes").toString());
                    Double avg_load = (Double) os.get("load_average");
                    list.add(rx_size_in_bytes.toString());
                    list.add(tx_size_in_bytes.toString());
                    list.add(index_time_in_millis.toString());
                    list.add(delete_time_in_millis.toString());
                    list.add(merge_time_in_millis.toString());
                    list.add(query_time_in_millis.toString());
                    list.add(avg_load.toString());
                }
            }
            return list;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }


}
