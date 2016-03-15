package com.kimmin.es.pressure;

import javax.mail.internet.PreencodedMimeBodyPart;

/**
 * Created by min.jin on 2016/3/15.
 */


public class PressureEngine {

    private final String ipAddr;
    private final long milliPeriod;

    private int searchThread = 0;
    private int createThread = 0;
    private int deleteThread = 0;

    public void startQuery(){

    }

    /** Getter & Setter **/
    public PressureEngine(String ipAddr, long milliPeriod){
        this.ipAddr = ipAddr;
        this.milliPeriod = milliPeriod;
    }

    public int getSearchThread() {
        return searchThread;
    }

    public void setSearchThread(int searchThread) {
        this.searchThread = searchThread;
    }

    public int getCreateThread() {
        return createThread;
    }

    public void setCreateThread(int createThread) {
        this.createThread = createThread;
    }

    public int getDeleteThread() {
        return deleteThread;
    }

    public void setDeleteThread(int deleteThread) {
        this.deleteThread = deleteThread;
    }

}
