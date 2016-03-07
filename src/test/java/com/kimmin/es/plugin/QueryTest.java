package com.kimmin.es.plugin;

import org.elasticsearch.action.admin.cluster.health.ClusterIndexHealth;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsIndices;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.junit.Test;

/**
 * Created by min.jin on 2016/3/7.
 */


public class QueryTest extends SampleTestCase{

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

        GetIndexResponse response = client.admin().indices().prepareGetIndex()
                .execute().actionGet();

        String[] indices = response.indices();

    }

    @Test
    public void test(){
        System.out.println("test");
    }

}
