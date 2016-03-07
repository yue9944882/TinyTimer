package com.kimmin.es.plugin;

import com.kimmin.mail.MailSendService;
import com.sun.glass.ui.SystemClipboard;

/**
 * Created by min.jin on 2016/3/7.
 */
public class MonitorThread extends Thread{

    public volatile boolean bRun = true;

    private int interval = 1000;

    public synchronized void setInterval(int millisecond){
        this.interval = millisecond;
    }

    @Override
    public void run(){
        while(bRun){
            if(System.currentTimeMillis()%interval == 0){
                /** Check Servers' Status **/


            }
        }
        /** Shutdown plugin **/
        ControllerSingleton.latch.countDown();
    }

}
