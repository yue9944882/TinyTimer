package com.kimmin.es.pressure;

import sun.net.www.http.HttpClient;

import javax.mail.internet.PreencodedMimeBodyPart;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by min.jin on 2016/3/15.
 */


public class PressureEngine {

    private final String ipAddr;
    private final int port;

    private static volatile Boolean bStop = false;

    public PressureEngine(String ipAddr, int port){
        this.ipAddr = ipAddr;
        this.port = port;
    }

    public void beginQueryPressure(int thread, long second){
        ExecutorService service = Executors.newFixedThreadPool(thread);

        service.shutdown();
    }

    public void beginDeletePressure(int thread, long second){
        ExecutorService service = Executors.newFixedThreadPool(thread);

        service.shutdown();
    }

    public void beginIndexPressure(int iThread, long second){
        Thread[] threads = new Thread[iThread];
        for(int i=0;i<iThread;i++){
            threads[i] = new ActualThread(ipAddr, port);
            threads[i].start();
        }
        long startSecond = new Date().getTime() / 1000;
        long endSecond = new Date().getTime() / 1000;

        while(endSecond < startSecond + second){
            try{
                Thread.currentThread().sleep(500);
                endSecond = new Date().getTime() / 1000;
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        bStop = true;

        for(int i=0;i<iThread;i++){
            try{
                threads[i].join();
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }

    public class ActualThread extends Thread{
        private volatile Boolean bStop;
        private String ipAddr;
        private int port;
        public ActualThread(String ipAddr, int port){
            this.ipAddr = ipAddr;
            this.port = port;
        }

        @Override
        public void run(){
            while(!PressureEngine.bStop){
                /** Once 10 request **/
                PressureUtils.createSomeRandomDoc(ipAddr, port, 10);
            }
        }
    }

}
