package com.kimmin.es.plugin.tiny.service;

/**
 * Created by kimmin on 3/8/16.
 */


public class AnalyzeService {
    /** Singleton **/
    private AnalyzeService(){}
    private static class Singleton{
        private static AnalyzeService instance = new AnalyzeService();
    }
    public AnalyzeService getInstance(){
        return Singleton.instance;
    }

    /** Take Snapshot **/
    public void snapshot(){

    }


}
