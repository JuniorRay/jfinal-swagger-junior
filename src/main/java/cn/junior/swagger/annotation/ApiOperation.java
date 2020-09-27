package cn.junior.swagger.annotation;

import cn.junior.swagger.config.DataType;
import cn.junior.swagger.config.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: ApiOperation
 * @Description:
 * @Author: Junior Ray
 * @Date:  2020/7/15 4:16 PM
 * @Version 1.0
 * @Copyright: Copyright (c) 2020 SoftSz All rights reserved
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiOperation {

    /**
     * url的路径值,如为空则通过 Route进行自动获取url
     *
     * @return
     */
    String value() default "";


    /**
     * 如果设置这个值、value的值不会被覆盖
     *
     * @return
     */
    String[] tags() default {};

    /**
     * 请求类型
     *
     * @return
     */
    RequestMethod[] methods() default {};

    String summary() default "";

    /**
     * 对api资源的描述
     *
     * @return
     */
    String description() default "";

    /**
     * 请求数据格式
     *
     * @return
     */
    String[] consumes() default {DataType.APPLICATION_JSON};

    /**
     * 返回数据格式
     *
     * @return
     */
    String[] produces() default {DataType.APPLICATION_JSON};

    /**
     * 是否显示
     */
    boolean hidden() default false;
}
