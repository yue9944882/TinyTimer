package com.kimmin.es.plugin.tiny.impl;

import com.kimmin.es.plugin.tiny.TinyMonitorComponent;
import com.kimmin.es.plugin.tiny.task.CycleTimingTask;
import com.kimmin.es.plugin.tiny.var.TimeDef;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kimmin on 3/8/16.
 */


/**
 * This class is for personal use
 *  Just ignore it
 * **/

public class FixedTimeDeleteTask {

    public FixedTimeDeleteTask(){
        taskName = "delete_at_fixed_freq";
        actualTask = new CycleTimingTask(
                new Runnable() {
                    public void run() {
                        /** Delete 7 day ago's index **/
                        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
                        String dateSuffix = format.format(
                                new Date(System.currentTimeMillis()-7*TimeDef.MILLI_PER_DAY));
                        TinyMonitorComponent.getClient().admin().indices().prepareCreate(
                                "clog_index_" + dateSuffix
                        ).execute().actionGet();
                    }
                }, taskName, TimeDef.MILLI_PER_DAY
        );
    }

    private CycleTimingTask actualTask;
    private final String taskName;

    public CycleTimingTask getTask(){
        return actualTask;
    }

    public String getTaskName(){
        return taskName;
    }
}
