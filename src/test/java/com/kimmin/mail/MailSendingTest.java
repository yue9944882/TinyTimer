package com.kimmin.mail;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by min.jin on 2016/3/7.
 */


public class MailSendingTest {

    @Test
    public void simpleTest(){

        Map<String, Object> dom = new HashMap<String, Object>();
        dom.put("name","kimmin");
        MailSendTask task = new MailSendTask(new File("d:\\template"),"tmp.ftl",dom,"ctripmailbot@126.com","yue9944882@126.com");
        task.setConfiguration("smtp.126.com","ctripmailbot","ctrip123456");
        ListenableFuture future = MailSendService.getInstance().provideTask(task);
        Futures.addCallback(future, new FutureCallback() {
            public void onSuccess(Object o) {
                System.out.println("Success!");
            }

            public void onFailure(Throwable throwable) {
                System.out.println("Failed..");
            }
        });

        try{
            future.get();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
