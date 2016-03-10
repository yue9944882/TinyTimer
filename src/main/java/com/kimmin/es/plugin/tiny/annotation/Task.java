package com.kimmin.es.plugin.tiny.annotation;

import java.lang.annotation.*;

/**
 * Created by kimmin on 3/8/16.
 */

@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Task {
    public String taskName() default "default_task_name";
}
