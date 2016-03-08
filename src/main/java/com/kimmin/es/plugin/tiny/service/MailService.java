package com.kimmin.es.plugin.tiny.service;

import com.kimmin.mail.MailSendService;

/**
 * Created by kimmin on 3/8/16.
 */


public class MailService {

    private String username = "";
    private String password = "";
    private String smtphost = "";

    /** Singleton **/
    private MailService(){}
    private static class Singleton{
        private static MailService instance = new MailService();
    }
    public static MailService getInstance(){
        return Singleton.instance;
    }

    public void setConfiguration(String username, String password, String smtphost){

    }


}
