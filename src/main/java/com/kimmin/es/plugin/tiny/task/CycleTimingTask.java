package com.kimmin.es.plugin.tiny.task;

import com.kimmin.es.plugin.tiny.exception.NoSuchTaskException;
import com.kimmin.es.plugin.tiny.service.RegisterService;
import com.kimmin.es.plugin.tiny.thread.TimingManager;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;

/**
 * Created by kimmin on 3/8/16.
 */


public class CycleTimingTask implements Runnable{

    /** Decorator **/
    private final Runnable oneTimeTask;
    public final long milliDelay;
    public final String taskName;

    private static ESLogger logger = Loggers.getLogger(CycleTimingTask.class);

    public CycleTimingTask(Runnable oneTimeTask, String taskName, long milliDelay){
        this.milliDelay = milliDelay;
        this.oneTimeTask = oneTimeTask;
        this.taskName = taskName;

    }

    public void run(){
        oneTimeTask.run();
        try{
            TimingManager.getInstance().startTask(this.taskName);
        }catch (NoSuchTaskException e){
            /** Maybe deleted but not unregistered **/
            RegisterService.getInstance().unregister(taskName);
        }
    }

}