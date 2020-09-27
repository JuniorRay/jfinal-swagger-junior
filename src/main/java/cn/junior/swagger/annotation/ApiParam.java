package cn.junior.swagger.annotation;

import cn.junior.swagger.config.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Title: ApiParam
 * @Description: 请求/返回参数
 * @Author: Junior Ray
 * @Date:  2020/7/15 4:16 PM
 * @Version 1.0
 * @Copyright: Copyright (c) 2020 SoftSz All rights reserved
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParam {
    /**
     * 参数名称
     *
     * @return
     */
    String name() default "";

    /**
     * 参数描述
     *
     * @return
     */
    String description() default "";

    /**
     * 是否必须
     *
     * @return
     */
    boolean required() default false;

    /**
     * 数据类型
     *
     * @return
     */
    String dataType() default DataType.String;

    /**
     * 默认值
     *
     * @return
     */
    String defaultValue() default "";

    /**
     * 参数类型
     */
    String paramType() default "query";
}
