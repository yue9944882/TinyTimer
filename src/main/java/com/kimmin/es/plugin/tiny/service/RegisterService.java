package com.kimmin.es.plugin.tiny.service;

import com.kimmin.es.plugin.tiny.exception.NoSuchTaskException;
import com.kimmin.es.plugin.tiny.task.CycleTimingTask;
import com.kimmin.es.plugin.tiny.thread.TimingManager;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.slf4j.Logger;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by kimmin on 3/8/16.
 */


public class RegisterService {
    /** Singleton **/
    private RegisterService(){}
    private static class Singleton{
        private static RegisterService instance = new RegisterService();
    }
    public static RegisterService getInstance(){
        return Singleton.instance;
    }

    /** Registering Infomation **/
    private ConcurrentHashMap<String, CycleTimingTask> registerMap = new ConcurrentHashMap<String, CycleTimingTask>();
    private ConcurrentHashMap<String, Boolean> enableMap = new ConcurrentHashMap<String, Boolean>();

    public static CountDownLatch latch = new CountDownLatch(1);
    private ESLogger logger = Loggers.getLogger(RegisterService.class);

    public void register(String taskName, CycleTimingTask task){
        registerMap.put(taskName, task);
    }

    public void unregister(String taskName){
        CycleTimingTask task = registerMap.<String> get(taskName);
        if(task == null){
            logger.warn("Cannot unregister {} task", taskName);
        }else{
            registerMap.remove(taskName);
            logger.info("Unregistered task: {}",taskName);
        }
    }

    public CycleTimingTask getTaskByName(String taskName) throws NoSuchTaskException{
        CycleTimingTask task = registerMap.<String> get(taskName);
        if(task == null){
            logger.error("Cannot find {} task", taskName);
            throw new NoSuchTaskException();
        }else{
            return task;
        }
    }

    public void enableTask(String taskName) throws NoSuchTaskException{
        Boolean enabled = RegisterService.getInstance().getTaskStatusByName(taskName);
        if(enabled != null) {
            if(enabled){
                /** Just do nothing here **/
            }else{
                TimingManager.getInstance().startTask(taskName);
            }
        }else{
            RegisterService.getInstance().enableTask(taskName);
        }
    }

    public void disableTask(String taskName){
        this.enableMap.put(taskName,false);
    }

    public Boolean getTaskStatusByName(String taskName){
        return this.enableMap.get(taskName);
    }

    public Map<String, CycleTimingTask> taskMap(){
        return this.registerMap;
    }

    public Map<String, Boolean> statusMap(){
        return this.enableMap;
    }

}


