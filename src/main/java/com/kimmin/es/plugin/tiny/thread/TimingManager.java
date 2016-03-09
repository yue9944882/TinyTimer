package com.kimmin.es.plugin.tiny.thread;

import com.kimmin.es.plugin.tiny.exception.NoSuchTaskException;
import com.kimmin.es.plugin.tiny.service.RegisterService;
import com.kimmin.es.plugin.tiny.task.CycleTimingTask;
import jdk.nashorn.internal.runtime.Timing;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kimmin on 3/8/16.
 */



public class TimingManager {
    /** Singleton **/
    private TimingManager(){}
    private static class Singleton{
        private static TimingManager instance = new TimingManager();
    }
    public static TimingManager getInstance(){ return Singleton.instance; }

    /** Constant Variable **/
    public final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private ScheduledExecutorService service = Executors.newScheduledThreadPool(CORE_POOL_SIZE);

    public void startTask(String taskName) throws NoSuchTaskException{
        CycleTimingTask task = RegisterService.getInstance().getTaskByName(taskName);
        service.schedule(task,task.milliDelay,TimeUnit.MILLISECONDS);
    }

    public void shutdown(){
        service.shutdown();
    }

}
