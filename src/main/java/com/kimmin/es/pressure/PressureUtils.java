package com.kimmin.es.pressure;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Created by min.jin on 2016/3/17.
 */

public class PressureUtils {

    public static void createTestIndex(String ipAddr, int port, int primShard, int repliShard){
        HttpClient client = new DefaultHttpClient();
        HttpPut put = new HttpPut("http://" + ipAddr
                + ":" + port + "/.test");
        Map map = new HashMap();
        Map indexMap = new HashMap();
        Map shardMap = new HashMap();
        shardMap.put("number_of_shards", primShard);
        shardMap.put("number_of_replicas", repliShard);
        indexMap.put("index", shardMap);
        map.put("settings", indexMap);
        JSONObject object = new JSONObject();
        object.putAll(map);
        put.setEntity(new StringEntity(
                object.toString(), ContentType.APPLICATION_JSON));
        try{
            HttpResponse response = client.execute(put);
            response.getStatusLine().getStatusCode();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public static void removeTestIndex(String ipAddr, int port){
        HttpClient client = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete("http://" + ipAddr + ":" + port + "/.test");
        try{
            HttpResponse response = client.execute(delete);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public static void createOneRandomDoc(String ipAddr, int port){
        HttpClient client = new DefaultHttpClient();
        Random random = new Random(System.currentTimeMillis());
        JSONObject object = new JSONObject();
        Map map = new HashMap();
        map.put("number", random.nextInt(Integer.MAX_VALUE));
        object.putAll(map);
        String urlBase = "http://" + ipAddr + ":" + port + "/.test/";
        Integer iType = random.nextInt(10);
        urlBase += iType.toString();
        String dId = new Integer(random.nextInt(Short.MAX_VALUE)).toString();
        urlBase = urlBase + "/" + dId;
        HttpPut put = new HttpPut(urlBase);
        put.setEntity(new StringEntity(object.toString(),ContentType.APPLICATION_JSON));
        try{
            HttpResponse response = client.execute(put);
            System.err.println(response.getStatusLine().getStatusCode());
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public static void createSomeRandomDoc(String ipAddr, int port, int count){
        for(int i=0;i<count;i++){
            createOneRandomDoc(ipAddr, port);
        }
    }

}
