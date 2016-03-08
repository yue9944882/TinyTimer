package com.kimmin.es.plugin;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthStatus;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.Client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by min.jin on 2016/3/7.
 */


public class MonitorServerStatus {

    private MonitorServerStatus(){
        date = System.currentTimeMillis()/MILLISEC_PER_DAY;
    }
    private static class StatusSingleton{
        private static MonitorServerStatus status = new MonitorServerStatus();
    }
    public static MonitorServerStatus getInstance(){
        return StatusSingleton.status;
    }

    private long date;/** Trigger for deleting index **/
    private Client client = null;

    public static final int MILLISEC_PER_DAY = 24*60*60*1000;
    public static final int PAST_DAY_TO_DELETE = 7;
    public MonitorThread monitorThread = null;

    public void selfCheck(){
        long now_date = System.currentTimeMillis()/MILLISEC_PER_DAY;
        if(++date == now_date){
            /** When it is a new day **/
            delete_7_day_ago_index();
            date = now_date;
        }
    }

    public void setClient(Client client){
        this.client = client;
    }

    public void delete_7_day_ago_index(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
        String suffix = format.format(new Date(System.currentTimeMillis()-PAST_DAY_TO_DELETE*MILLISEC_PER_DAY));
        String indexName = "clog_index_" + suffix;
        DeleteIndexResponse response2 = client.admin().indices().prepareDelete(indexName)
                .execute().actionGet();
    }

}
