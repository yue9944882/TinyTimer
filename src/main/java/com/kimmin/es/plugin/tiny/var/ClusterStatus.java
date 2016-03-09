package com.kimmin.es.plugin.tiny.var;

/**
 * Created by kimmin on 3/9/16.
 */

public enum ClusterStatus {

    GREEN("GREEN"),
    YELLOW("YELLOW"),
    RED("RED");

    private String status;

    ClusterStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

}
