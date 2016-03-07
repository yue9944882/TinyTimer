package com.kimmin.mail;

/**
 * Created by min.jin on 2016/2/26.
 */


public class MailSendException extends Exception {

    MailSendException(String from, String to){
        this.from = from;
        this.to = to;
    }

    String from = "";
    String to = "";

    public String getFrom(){
        return from;
    }
    public String getTo(){
        return to;
    }
}
