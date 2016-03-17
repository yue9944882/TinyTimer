package com.kimmin.jmeter;

import com.kimmin.es.plugin.tiny.TinyTimerComponent;
import com.kimmin.es.plugin.tiny.service.AnalyzeService;
import com.kimmin.es.pressure.PressureEngine;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.junit.Test;

import java.util.Map;

/**
 * Created by kimmin on 3/9/16.
 */


public class PressureTest {

    @Test
    public void simpleIndexPressure(){
        PressureEngine engine = new PressureEngine("10.2.58.217",9200);
        engine.beginIndexPressure(5,3);
    }

}
