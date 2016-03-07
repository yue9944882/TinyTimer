package com.kimmin.es.plugin;

import com.kimmin.mail.MailSendService;
import com.sun.glass.ui.SystemClipboard;
import org.elasticsearch.client.Client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by min.jin on 2016/3/7.
 */
public class MonitorThread extends Thread{

    public volatile boolean bRun = true;

    @Override
    public void run(){
//        while(bRun){
//            if(System.currentTimeMillis() % MonitorInterval.getCheckInterval()== 0){
//                /** Check Servers' Status **/
//                client.admin().cluster().prepareClusterStats();
//
//
//            }
//        }


        /** Shutdown plugin **/
        ControllerSingleton.latch.countDown();
    }

}
