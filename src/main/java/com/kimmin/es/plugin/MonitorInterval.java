package com.kimmin.es.plugin;

import java.util.concurrent.locks.

/**
 * Created by min.jin on 2016/3/7.
 */
public class MonitorInterval {

    public final static int CHECK_PER_SECOND = 1000;
    public final static int CHECK_PER_MINUTE = 60*1000;
    public final static int CHECK_PER_HOUR = 60*60*1000;

    private static int checkInterval = CHECK_PER_SECOND;

    public synchronized static void setCheckInterval(int millisecond){
        this.checkInterval
    }

}
