package com.kimmin.es.plugin.tiny.impl;

import com.kimmin.es.plugin.tiny.annotation.Task;
import com.kimmin.es.plugin.tiny.service.AnalyzeService;
import com.kimmin.es.plugin.tiny.task.AbstractTask;
import com.kimmin.es.plugin.tiny.task.CycleTimingTask;
import com.kimmin.es.plugin.tiny.var.TimeDef;

/**
 * Created by kimmin on 3/9/16.
 */

@Task
public class SnapshotTask extends AbstractTask {

    public SnapshotTask(){
        this.taskName = "snapshot_task";
        this.actualTask = new CycleTimingTask(new Runnable() {
            public void run() {
                AnalyzeService.getInstance().snapshot("19300");// Using default 9200 port
            }
        },this.taskName, TimeDef.MILLI_PER_MINUTE);
    }

    private final String taskName;
    private CycleTimingTask actualTask;

    @Override
    public CycleTimingTask getTask(){
        return actualTask;
    }

    @Override
    public String getTaskName(){
        return taskName;
    }




}
