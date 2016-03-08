package com.kimmin.es.plugin.tiny.service;

import com.kimmin.es.plugin.tiny.exception.NoSuchTaskException;
import com.kimmin.es.plugin.tiny.task.CycleTimingTask;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

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

}
