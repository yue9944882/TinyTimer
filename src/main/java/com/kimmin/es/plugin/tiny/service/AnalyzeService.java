package com.kimmin.es.plugin.tiny.service;

import com.kimmin.es.plugin.tiny.TinyTimerComponent;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;

/**
 * Created by kimmin on 3/8/16.
 */


public class AnalyzeService {
    /** Singleton **/
    private AnalyzeService(){}
    private static class Singleton{
        private static AnalyzeService instance = new AnalyzeService();
    }
    public static AnalyzeService getInstance(){
        return Singleton.instance;
    }

    /** Take Snapshot **/
    public void snapshot(){

    }
    /** Do Pressure Test **/
    public void pressure(){
        beforePressure();


        afterPressure();
    }

    public void beforePressure(){
        /** Create an index for writing **/
        CreateIndexResponse response = TinyTimerComponent.getClient()
                .admin().indices().prepareCreate(".test")
                .execute().actionGet();
    }

    public void afterPressure(){
        /** Delete the index for test **/
        DeleteIndexResponse response = TinyTimerComponent.getClient()
                .admin().indices().prepareDelete(".test")
                .execute().actionGet();
    }

}
