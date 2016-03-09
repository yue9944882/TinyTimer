package com.kimmin.es.plugin.tiny.mail;

/**
 * Created by kimmin on 3/8/16.
 */

public enum MailLevel {
    INFO("info"),
    WARN("warn");

    final String level;

    MailLevel(String level){
        this.level = level;
    }

    public String getLevel(){
        return level;
    }

}
