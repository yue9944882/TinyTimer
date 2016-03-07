package com.kimmin.es.plugin;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthStatus;
import org.elasticsearch.action.admin.cluster.health.ClusterIndexHealth;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsIndices;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.collect.HppcMaps;
import org.elasticsearch.search.SearchHit;
import org.junit.Test;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by min.jin on 2016/3/7.
 */


public class QueryTest extends SampleTestCase{

    private static ObjectMapper om = new ObjectMapper();

    @Test
    public void query1(){
//        SearchResponse response = client.prepareSearch("test")
//                .addFields("url","_source")
//                .execute().actionGet();
//        for(SearchHit hit: response.getHits()){
//            if(hit.getFields().containsKey("url")){
//                System.out.println(hit.toString());
//            }
//        }

        ClusterStatsResponse response = client.admin().cluster().prepareClusterStats()
                .execute().actionGet();
        ClusterHealthStatus health = response.getStatus();

    }

    @Test
    public void test(){
        System.out.println("test");
    }

    @Test
    public void testObjMapping(){
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(restBaseUrl+"/_stats?pretty");
        httpGet.addHeader("accept","application/json");
        try{
            HttpResponse response = client.execute(httpGet);
            HttpEntity respEntity = response.getEntity();
            if(response.getStatusLine().getStatusCode() == 200){
                Map map = om.readValue(respEntity.getContent(),Map.class);
                /** Do Validation **/
                map.get("13");
                /** Process stats **/

            }

        }catch (IOException ioe){
            ioe.printStackTrace();
        }


    }

}
