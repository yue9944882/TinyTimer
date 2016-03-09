package com.kimmin.json;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by min.jin on 2016/3/7.
 */
public class RestToObj {

    @Test
    public void getTime(){
        System.out.println(new Date().getDay());
    }

    @Test
    public void scanPackage() throws IOException{
        String pkgName = RestToObj.class.getPackage().getName();
        String pkgDirName = pkgName.replace('.','/');
        System.out.println(pkgDirName);
        URL url = Thread.currentThread().getContextClassLoader().getResource(pkgDirName);



    }

}
