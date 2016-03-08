package com.kimmin.es.plugin;

import com.google.common.util.concurrent.Monitor;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.network.NetworkAddress;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.node.Node;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.test.ESIntegTestCase;
import org.junit.Before;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

/**
 * Created by min.jin on 2016/3/7.
 */


public class SampleTestCase extends ESIntegTestCase {

    protected String restBaseUrl;
    protected Client client;

    @Override
    protected Settings nodeSettings(int nodeOrdinal){
        return Settings.builder()
                .put(super.nodeSettings(nodeOrdinal))
                .put(Node.HTTP_ENABLED,true)
                .build();
    }

    @Override
    protected Collection<Class<? extends Plugin>> nodePlugins(){
        return Arrays.<Class<? extends Plugin>> asList(MonitorPlugin.class);
    }

    protected final static String INDEX_NAME = "test";

    @Before
    public void createTestIndex() throws Exception {
        if(!client().admin().indices().prepareExists(INDEX_NAME).get().isExists()){
            this.client = client();

            BulkRequestBuilder bulk = client.prepareBulk();
            for (String[] data : SampleDocumentData.SAMPLE_DATA) {
                bulk.add(client.prepareIndex()
                        .setIndex(INDEX_NAME)
                        .setType("test")
                        .setSource(XContentFactory.jsonBuilder()
                                .startObject()
                                .field("url",     data[0])
                                .field("title",   data[1])
                                .field("content", data[2])
                                .endObject()));
            }
            bulk.add(client.prepareIndex()
                    .setIndex(INDEX_NAME)
                    .setType("empty")
                    .setSource(XContentFactory.jsonBuilder()
                            .startObject()
                            .field("url",     "")
                            .field("title",   "")
                            .field("content", "")
                            .endObject()));


            bulk.setRefresh(true).execute().actionGet();
        }


        for(int i=0;i<10;i++){
            Date date = new Date(System.currentTimeMillis()-i* MonitorServerStatus.MILLISEC_PER_DAY);
            SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
            CreateIndexResponse response = client.admin().indices().prepareCreate("clog_index_"+format.format(date))
                    .execute().actionGet();
        }


        ensureGreen(INDEX_NAME);

        InetSocketAddress endpoint = randomFrom(cluster().httpAddresses());
        this.restBaseUrl = "http://" + NetworkAddress.formatAddress(endpoint);
    }




}
