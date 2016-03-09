package com.kimmin.es.plugin.tiny.service;

import com.kimmin.es.plugin.tiny.TinyTimerComponent;
import com.kimmin.es.plugin.tiny.mail.MailDom;
import com.kimmin.es.plugin.tiny.mail.MailLevel;
import com.kimmin.es.plugin.tiny.thread.TimingManager;
import com.kimmin.es.plugin.tiny.var.ClusterStatus;
import com.kimmin.es.plugin.tiny.var.Log;
import com.kimmin.es.plugin.tiny.var.LogQueue;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

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

    private ClusterStatus status = ClusterStatus.GREEN;
    private ObjectMapper om = new ObjectMapper();

    public LogQueue queue = new LogQueue();

    /** Take Snapshot **/
    public void snapshot(String port){
        if(port.equals("")) port = "9200";
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:" + port + "/_stats?pretty");
        httpGet.addHeader("accept","application/json");
        try{
            HttpResponse response = client.execute(httpGet);
            HttpEntity respEntity = response.getEntity();
            if(response.getStatusLine().getStatusCode() == 200){
                Map map = om.readValue(respEntity.getContent(), Map.class);
                /** Squeeze data **/
                Map primaryMap = ((Map)((Map)map.get("_all")).get("primaries"));
                double index_time = Double.parseDouble((String)((Map)primaryMap.get("indexing"))
                        .get("index_time_in_millis"));
                double search_time = Double.parseDouble((String)((Map)primaryMap.get("search"))
                        .get("query_time_in_millis"));
                double merge_time = Double.parseDouble((String)((Map)primaryMap.get("merges"))
                        .get("total_time_in_millis"));
                double refresh_time = Double.parseDouble((String)((Map)primaryMap.get("refresh"))
                        .get("total_time_in_millis"));
                double flush_time = Double.parseDouble((String)((Map)primaryMap.get("flush"))
                        .get("total_time_in_millis"));
                Log log = new Log(index_time,search_time,merge_time,refresh_time,flush_time);
                queue.addNew(log);
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        /** Monitoring ES cluster status **/
        ClusterStatsResponse response = TinyTimerComponent.getClient()
                .admin().cluster().prepareClusterStats()
                .execute().actionGet();
        ClusterStatus state = ClusterStatus.valueOf(response.getStatus().toString());
        if(state != this.status){
            Map map = new LinkedHashMap();
            map.put("value","[INFO]: Cluster Status Changed to : "+state.getStatus());
            MailDom dom = new MailDom(map);
            MailService.getInstance().sendTo("yue9944882@126.com", MailLevel.INFO,dom);
        }
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

    private void onStatusChanged(){
        /** Giving Alert via Mail **/
    }

    public void analyzeLogQueue(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Iterator<Log> iter = queue.blockingQueue.iterator();
        while(iter.hasNext()){
            Log log = iter.next();
            dataset.addValue(log.search_time, "searchTime", "");
            dataset.addValue(log.merge_time, "merge_time", "");
            dataset.addValue(log.index_time, "index_time", "");
            dataset.addValue(log.refresh_time, "refresh_time", "");
            dataset.addValue(log.flush_time, "flush_time", "");
        }
        JFreeChart jFreeChart = ChartFactory.createLineChart("ANALYZE","Category","Time",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        try{
            ChartUtilities.saveChartAsPNG(new File("tmp.png"), jFreeChart, 800, 800);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
