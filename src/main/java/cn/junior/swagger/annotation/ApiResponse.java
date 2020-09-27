package cn.junior.swagger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Title: ApiResponse
 * @Description: 接口返回参数（可多个不同返回）
 * @Author: Junior Ray
 * @Date:  2020/7/15 4:16 PM
 * @Version 1.0
 * @Copyright: Copyright (c) 2020 SoftSz All rights reserved
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponse {
    /**
     * http响应状态码
     *
     * @return
     */
    String code() default "200";

    /**
     * 描述
     *
     * @return
     */
    String message() default "OK";

    /**
     * 返回类型
     *
     * @return
     */
    Class<?> response() default Void.class;

}
