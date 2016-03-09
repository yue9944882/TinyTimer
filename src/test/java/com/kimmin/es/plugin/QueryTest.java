package com.kimmin.es.plugin;

import com.kimmin.es.plugin.tiny.TinyTimerComponent;
import com.kimmin.es.plugin.tiny.exception.NoSuchTaskException;
import com.kimmin.es.plugin.tiny.service.AnalyzeService;
import com.kimmin.es.plugin.tiny.service.RegisterService;
import com.kimmin.es.plugin.tiny.var.ClusterStatus;
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
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.collect.HppcMaps;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.TransportService;
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

        System.out.println(response.getStatus().toString());

        ClusterStatus state = ClusterStatus.valueOf(response.getStatus().toString());

        //org.elasticsearch.action.bulk.TransportBulkAction


    }

    @Test
    public void showAllIndices(){
//        ClusterStatsResponse responses = client.admin().cluster().prepareClusterStats()
//                .execute().actionGet();
//        responses.getIndicesStats();

        GetIndexResponse response = client.admin().indices().prepareGetIndex()
                .execute().actionGet();
        String[] names = response.getIndices();///Index Names
        for(String name: names){
            System.out.println(name);
        }

        //MonitorServerStatus.getInstance().delete_7_day_ago_index();

        GetIndexResponse response2 = client.admin().indices().prepareGetIndex()
                .execute().actionGet();
        String[] names2 = response2.getIndices();///Index Names
        for(String name: names2){
            System.out.println(name);
        }

    }


    @Test
    public void test(){
        System.out.println("test");
        TransportService transportService = null;
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

            }

        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    @Test
    public void bulkTest(){
//        BulkResponse response = client.prepareBulk()
//                .add()..
    }


    @Test
    public void testPressureTest(){
        AnalyzeService.getInstance().beforePressure();

        ClusterStatsResponse response = TinyTimerComponent.getClient().admin().cluster().prepareClusterStats()
                .execute().actionGet();

        System.out.println(response.toString());

        AnalyzeService.getInstance().afterPressure();
    }

    @Test
    public void testHandler() throws InterruptedException{
        while(true){
            Thread.currentThread().sleep(1000);
            for(int i=1;i<100000;i++){
                i++;
            }
        }
    }

    @Test
    public void testSnapshot() throws NoSuchTaskException, InterruptedException {

        RegisterService.latch.await();

        RegisterService.getInstance().enableTask("snapshot_task");
        /** Wating five sec **/

        for(int i=0;i<100;i++){
            try {
                IndexResponse response = client.prepareIndex("test", "snap", "val" + i)
                        .setSource(XContentFactory.jsonBuilder()
                                .startObject()
                                .field("123", "hahah")
                                .endObject())
                        .execute().actionGet();
                if(response.isCreated())System.out.println("YES");
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }

        testHandler();
    }




}
