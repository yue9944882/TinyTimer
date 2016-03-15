package com.kimmin.es.plugin.tiny.impl;

import com.kimmin.es.plugin.tiny.annotation.Task;
import com.kimmin.es.plugin.tiny.task.AbstractTask;
import com.kimmin.es.plugin.tiny.task.CycleTimingTask;
import com.kimmin.es.plugin.tiny.var.TimeDef;

/**
 * Created by kimmin on 3/9/16.
 */


@Task
public class FakeTask extends AbstractTask{

    public FakeTask(){
        this.taskName = "a_fake_task";
        this.actualTask = new CycleTimingTask(new Runnable() {
            public void run() {
                System.out.println("Fake Test Heart-beat");
            }
        },this.taskName, TimeDef.MILLI_PER_SECOND);
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
    };

}
