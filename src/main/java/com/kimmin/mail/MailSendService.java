package com.kimmin.mail;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by min.jin on 2016/2/26.
 */



public class MailSendService extends AbstractExecutionThreadService{

    /** Singleton Pattern **/
    private MailSendService(){}

    private static class Singleton{
        private static MailSendService instace = new MailSendService();
    }

    public static MailSendService getInstance(){
        return Singleton.instace;
    }

    /** Core Asynchronous Support **/
    private ListeningExecutorService service = MoreExecutors.listeningDecorator(
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    /** Logging Support **/
    private Logger logger = Logger.getLogger(MailSendService.class);


    @Override
    public void run() {
        try {
            while(true){
                logger.info("------ HEART BEAT ------");
                Thread.currentThread().sleep(1000*5);
            }
        }catch(Throwable e){
            e.printStackTrace();
        }
    }

    public ListenableFuture<Boolean> provideTask(MailSendTask task){
        logger.info("A Mail-sending is received by service..");
        return service.submit(task);
    }

    @Override
    public void triggerShutdown(){
        service.shutdown();
    }


}
