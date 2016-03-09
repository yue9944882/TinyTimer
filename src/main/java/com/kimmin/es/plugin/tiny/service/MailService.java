package com.kimmin.es.plugin.tiny.service;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.kimmin.es.plugin.tiny.mail.MailLevel;
import com.kimmin.mail.MailSendService;
import com.kimmin.mail.MailSendTask;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kimmin on 3/8/16.
 */


public class MailService {

    private String username = "";
    private String password = "";
    private String smtphost = "";

    private ESLogger logger = Loggers.getLogger(MailService.class);

    /** Singleton **/
    private MailService(){}
    private static class Singleton{
        private static MailService instance = new MailService();
    }
    public static MailService getInstance(){
        return Singleton.instance;
    }

    public void setConfiguration(String username, String password, String smtphost){
        this.username = username;
        this.password = password;
        this.smtphost = smtphost;
    }

    public void sendTo(String destination,MailLevel level) throws NullPointerException {
        Map<String, Object> dom = new HashMap<String, Object>();
        dom.put("info","kimmin");

        String ftlPath = MailService.class.getClassLoader().getResource(level.getLevel()+".ftl").getPath();
        File ftlFile = new File(ftlPath);
        MailSendTask task = new MailSendTask(ftlFile.getParentFile(),ftlFile.getName(),
                dom,this.username,destination);

        task.setConfiguration(this.smtphost,this.username,this.password);
        ListenableFuture future = MailSendService.getInstance().provideTask(task);
        Futures.addCallback(future, new FutureCallback() {
            public void onSuccess(Object o) {
                logger.info("A mail successfully sent!");
            }
            public void onFailure(Throwable throwable) {
                logger.error("A mail failed to sent..");
            }
        });

        try{
            future.get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }





}
