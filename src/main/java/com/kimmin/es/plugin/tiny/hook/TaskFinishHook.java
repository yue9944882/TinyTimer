package com.kimmin.es.plugin.tiny.hook;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.kimmin.es.plugin.tiny.exception.NoSuchTaskException;
import com.kimmin.es.plugin.tiny.service.RegisterService;
import com.kimmin.es.plugin.tiny.task.CycleTimingTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by min.jin on 2016/3/14.
 */
public class TaskFinishHook implements Runnable{

    public static void addCycleHook(ListeningScheduledExecutorService service, ListenableFuture future, String taskName){
        /** Let the task get done again & again **/
        future.addListener(new TaskFinishHook(service, taskName), service);
    }

    /** Can only be instanced via its PUBLIC STATIC method **/
    private TaskFinishHook(ListeningScheduledExecutorService service, String taskName){
        this.service = service;
        this.taskName = taskName;
    }

    private final ListeningScheduledExecutorService service;
    private final String taskName;

    /** Task of Appending task again into the executor service **/
    public void run(){
        try {
            CycleTimingTask task = RegisterService.getInstance().getTaskByName(taskName);
            service.schedule(task, task.milliDelay , TimeUnit.MILLISECONDS);
        }catch (NoSuchTaskException e){
            e.printStackTrace();
        }
    }

}
