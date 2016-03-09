package com.kimmin.json;

import com.kimmin.es.plugin.tiny.exception.NoSuchTaskException;
import com.kimmin.es.plugin.tiny.impl.ImplScanner;
import com.kimmin.es.plugin.tiny.service.RegisterService;
import com.kimmin.es.plugin.tiny.task.CycleTimingTask;
import org.junit.Test;

/**
 * Created by kimmin on 3/8/16.
 */
public class TestAnnotation {

    @Test
    public void doTest() {
        ImplScanner.scanImpl();
        try {
            CycleTimingTask task = RegisterService.getInstance().getTaskByName("delete_at_fixed_freq");
            if(task != null){
                System.out.println("OK");
            }
        }catch (NoSuchTaskException e){
            e.printStackTrace();
        }
    }
}
