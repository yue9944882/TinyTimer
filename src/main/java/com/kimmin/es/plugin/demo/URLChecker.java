package com.kimmin.es.plugin.demo;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;


/**
 * Created by min.jin on 2016/2/26.
 */

public class URLChecker implements Runnable {

    private Client client;
    private ESLogger logger;
    private String url;
    private int time;
    private HttpClient httpClient;

    public URLChecker(Client client,String url,String time,ESLogger logger){
        this.client = client;
        this.time = Integer.parseInt(time);
        this.url = url;
        this.logger = logger;
        this.httpClient = new DefaultHttpClient();
    }

    @Override
    public void run(){
        while(true){
            logger.info("Checking:{}",url);
            HttpHead headMethod = new HttpHead(url);
            try{
                HttpResponse httpResponse = httpClient.execute(headMethod);
                int respCode = httpResponse.getStatusLine().getStatusCode();
                if(respCode< HttpStatus.SC_OK || respCode>HttpStatus.SC_MULTI_STATUS){
                    logger.error("Error,got {} code",respCode);
                }else{
                    Header header = httpResponse.getFirstHeader("Last-Modified");
                    if(header!=null){
                        indexLastModified(header.getValue());
                    }else{
                        logger.warn("{} didn't return last modified",url);
                    }
                }
            }catch (Exception exception){
                logger.error("Error during URLChecker execution",exception);
            }finally {
                headMethod.releaseConnection();
            }
            logger.info("Sleeping for {} minutes",time);
            try{
                Thread.sleep(1000*60*time);
            }catch (InterruptedException ie){
                logger.error("Thread Interrupted",ie);
            }
        }
    }

    protected void indexLastModified(String modified) throws IOException{
        client.prepareIndex("urls","url",url)
                .setSource(XContentFactory.jsonBuilder()
                .startObject()
                    .field("url",url)
                    .field("modified",modified)
                .endObject()).execute().actionGet();
    }

}
