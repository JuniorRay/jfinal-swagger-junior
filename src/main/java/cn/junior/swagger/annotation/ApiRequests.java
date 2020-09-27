package cn.junior.swagger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Title: ApiRequests
 * @Description: 接口请求参数
 * @Author: Junior Ray
 * @Date:  2020/7/15 4:16 PM
 * @Version 1.0
 * @Copyright: Copyright (c) 2020 SoftSz All rights reserved
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiRequests {
    Class<?> request() default Void.class;
}
