package com.kimmin.es.plugin;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.kimmin.mail.MailSendService;
import com.kimmin.mail.MailSendTask;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthStatus;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.Client;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    /** Monitoring Varriable **/
    private long date;/** Trigger for deleting index **/
    private ClusterHealthStatus status;

    private Client client = null;

    public static final int MILLISEC_PER_DAY = 24*60*60*1000;
    public static final int PAST_DAY_TO_DELETE = 7;
    public MonitorThread monitorThread = null;

    public void selfCheck(){

        /** Updating & Deleting Index **/
        long now_date = System.currentTimeMillis()/MILLISEC_PER_DAY;
        if(++date == now_date){
            /** When it is a new day **/
            delete_7_day_ago_index();
            date = now_date;
        }

        /** Checking Cluster Status **/
        if(client != null) {
            ClusterStatsResponse response = client.admin().cluster().prepareClusterStats()
                    .execute().actionGet();

            ClusterHealthStatus status = response.getStatus();
            if (status != this.status) {
                sendClusterStatusMail("Cluster Status Changed!!");
            }
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

    public void sendClusterStatusMail(String info){
        String statusMailPath = MonitorServerStatus.class.getClassLoader().getResource("tmp.ftl").getPath();
        Map<String, Object> dom = new HashMap<String, Object>();
        dom.put("info",info);
        MailSendTask task = new MailSendTask(new File(statusMailPath).getParentFile(),"tmp.ftl",dom,"ctripmailbot@126.com","yue9944882@126.com");
        task.setConfiguration("smtp.126.com","ctripmailbot","ctrip123456");
        ListenableFuture future = MailSendService.getInstance().provideTask(task);
        Futures.addCallback(future, new FutureCallback() {
            public void onSuccess(Object o) {
                System.out.println("Success!");
            }

            public void onFailure(Throwable throwable) {
                System.out.println("Failed..");
            }
        });

        try{
            future.get();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
