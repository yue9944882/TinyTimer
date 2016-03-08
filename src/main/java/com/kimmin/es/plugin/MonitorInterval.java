package com.kimmin.es.plugin;


/**
 * Created by min.jin on 2016/3/7.
 */
public class MonitorInterval {

    public final static long CHECK_PER_SECOND = 1000;
    public final static long CHECK_PER_MINUTE = 60*1000;
    public final static long CHECK_PER_HOUR = 60*60*1000;

    private volatile static long checkInterval = CHECK_PER_SECOND;

    public synchronized static void setCheckInterval(int millisecond) {
        if (millisecond > CHECK_PER_SECOND
                && millisecond < CHECK_PER_HOUR) {
            checkInterval = millisecond;
        }
    }

    public static long getCheckInterval(){
        return checkInterval;
    }

}
