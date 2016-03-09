package com.kimmin.es.plugin.tiny.var;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by kimmin on 3/9/16.
 */
public class LogQueue {

    public static final int MAX_QUEUE_ELEMENT = 100000;

    public BlockingQueue<Log> blockingQueue = new ArrayBlockingQueue<Log>(MAX_QUEUE_ELEMENT, true);

    public boolean isEmpty(){
        return blockingQueue.isEmpty();
    }

    public boolean isMax(){
        return blockingQueue.remainingCapacity() == 0;
    }

    public void removeOld(){
        /** Maybe we can dump it into a DB? **/
        blockingQueue.poll();
    }

    public void addNew(Log log){
        if(isMax()){
            removeOld();
        }
        blockingQueue.offer(log);
    }
}
