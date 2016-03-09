package com.kimmin.es.plugin.tiny.impl;

import com.kimmin.es.plugin.tiny.annotation.Task;
import com.kimmin.es.plugin.tiny.service.RegisterService;
import com.kimmin.es.plugin.tiny.task.AbstractTask;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by kimmin on 3/8/16.
 */


public class ImplScanner {

    private static ESLogger logger = Loggers.getLogger(ImplScanner.class);

    public static void scanImpl(){

        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();

        String pkgName = ImplScanner.class.getPackage().getName();
        String pkgDirName = pkgName.replace('.','/');
        logger.info("Begin scanning the directory..");
        URL url = Thread.currentThread().getContextClassLoader().getResource(pkgDirName);
        String protocol = url.getProtocol();
        if(protocol.equals("file")){
            logger.info("FILE protocol.");
            try {
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                findAndAddClassesInPackageByFile(pkgName, filePath, classes);
            }catch (UnsupportedEncodingException e){
                e.fillInStackTrace();
            }
        }else if(protocol.equals("jar")){
            logger.info("JAR protocol");
            JarFile jar;
            try {
                jar = ((JarURLConnection) url.openConnection()).getJarFile();
                Enumeration<JarEntry> entries = jar.entries();
                while(entries.hasMoreElements()){
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if(name.charAt(0) == '/'){
                        name = name.substring(1);
                    }
                    if(name.startsWith(pkgDirName)){
                        int idx = name.lastIndexOf('/');
                        if(idx == -1){
                            pkgName = name.substring(0,idx).replace('/', '.');
                        }else{
                            if(name.endsWith(".class")
                                    && !entry.isDirectory()){
                                String className = name.substring(pkgName.length()+1,
                                        name.length()-".class".length());
                                try{
                                    classes.add(Class.forName(pkgName + "." + className));
                                }catch (ClassNotFoundException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
        /** Check if annotated **/
        for(Class clazz: classes){
            for(Annotation annotation: clazz.getAnnotations()){
                if(annotation.annotationType() == Task.class
                        && clazz.getSuperclass() == AbstractTask.class){
                    /** Register task classes **/
                    try {
                        AbstractTask task = (AbstractTask) clazz.newInstance();
                        RegisterService.getInstance()
                                .register(task.getTaskName(), task.getTask());
                        RegisterService.getInstance().disableTask(task.getTaskName());
                    }catch (Throwable e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void findAndAddClassesInPackageByFile(String pkgName,
                                                        String pkgPath,
                                                        Set<Class<?>> classes){
        File dir = new File(pkgPath);
        if(!dir.exists() || !dir.isDirectory()){
            return;
        }
        File[] dirfiles = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".class");
            }
        });
        for(File file: dirfiles){
            String className = file.getName()
                    .substring(0,file.getName().length()-".class".length());
            try{
                classes.add(Thread.currentThread().getContextClassLoader()
                .loadClass(pkgName + "." + className));
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }

    }

}
