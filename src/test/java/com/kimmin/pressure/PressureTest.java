package com.kimmin.pressure;

import com.kimmin.es.monitor.MonitorEngine;
import com.kimmin.es.pressure.PressureEngine;
import com.kimmin.es.pressure.PressureUtils;
import org.junit.Test;


import javax.mail.internet.PreencodedMimeBodyPart;
import java.io.File;

/**
 * Created by min.jin on 2016/3/17.
 */
public class PressureTest {

    @Test
    public void testPressure(){
        final PressureEngine pressureEngine = new PressureEngine("10.2.58.217",9200);
        final MonitorEngine monitorEngine = new MonitorEngine("10.2.58.217",9200);
        PressureUtils.createTestIndex("10.2.58.217",9200,1,1);
        PressureUtils.createOneRandomDoc("10.2.58.217",9200);
        final File file = new File("test.csv");
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                monitorEngine.dumpStatsToCsv(file ,1,61);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                pressureEngine.beginIndexPressure(10,60); // 10 thread for 10sec
            }
        });
//        monitorEngine.dumpStatsToCsv(new File("test.csv"),1,70);
//        pressureEngine.beginIndexPressure(10,60); // 10 thread for 10sec
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }

    @Test
    public void removePressure(){
        PressureUtils.removeTestIndex("10.2.58.217",9200);
    }
}
