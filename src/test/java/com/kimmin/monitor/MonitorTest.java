package com.kimmin.monitor;

import com.kimmin.es.monitor.MonitorEngine;
import com.kimmin.es.pressure.PressureUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by min.jin on 2016/3/15.
 */


public class MonitorTest {

    @Test
    public void testMonitor(){
        System.out.println("---- JVM ----");
        MonitorEngine engine = new MonitorEngine("10.2.58.217",9200);
        List<String> list = engine.snapshotAndMapJVMToStrings();
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        /** Again **/
        System.out.println("---- Again ----");
        list = engine.snapshotAndMapJVMToStrings();
        iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println("---- CRUD ----");
        List<String> crud_list = engine.snapshotAndMapCRUDToStrings();
        Iterator<String> crud_iter = crud_list.iterator();
        while(crud_iter.hasNext()){
            System.out.println(crud_iter.next());
        }
    }



    @Test
    public void anyTest(){
        System.out.println(Integer.MAX_VALUE);
    }


    @Test
    public void testOutputCsv(){
        MonitorEngine engine = new MonitorEngine("10.2.58.217",9200);
        File file = new File("test.csv");
        engine.dumpStatsToCsv(file,2,4);
    }

    @Test
    public void testUtil(){
//        PressureUtils.createTestIndex("10.2.58.217",9200,5,1);
        PressureUtils.removeTestIndex("10.2.58.217",9200);
    }

    @Test
    public void testRandom(){
        Random random = new Random(System.currentTimeMillis());
        for(int i=0;i<10;i++){
            System.out.println(random.nextInt(10));
        }
    }

}
