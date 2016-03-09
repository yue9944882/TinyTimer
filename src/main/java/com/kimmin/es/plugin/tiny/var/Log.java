package com.kimmin.es.plugin.tiny.var;

/**
 * Created by kimmin on 3/9/16.
 */


public class Log {
    public Log(double index_time, double search_time, double merge_time,
               double refresh_time, double flush_time){
        this.index_time = index_time;
        this.search_time = search_time;
        this.merge_time = merge_time;
        this.refresh_time = refresh_time;
        this.flush_time = flush_time;
    }

    public double index_time;
    public double search_time;
    public double merge_time;
    public double refresh_time;
    public double flush_time;

}
