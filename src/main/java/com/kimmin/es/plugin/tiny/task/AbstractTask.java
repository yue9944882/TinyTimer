package com.kimmin.es.plugin.tiny.task;

/**
 * Created by kimmin on 3/8/16.
 */
public abstract class AbstractTask {

    public AbstractTask(){}

    private String taskName;
    private CycleTimingTask cycleTimingTask;

    public String getTaskName(){
        return taskName;
    }

    public CycleTimingTask getTask(){
        return cycleTimingTask;
    }

}
