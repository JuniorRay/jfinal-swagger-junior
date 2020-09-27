package cn.junior.swagger.config;

import cn.junior.swagger.annotation.ApiOperation;

import java.lang.annotation.Annotation;

/**
 * @Title: JuniorSwaggerConstant
 * @Description:
 * @Author:Junior Ray
 * @Date: 2020/7/15 4:16 PM
 * @Version 1.0
 * @Copyright: Copyright (c) 2020 SoftSz All rights reserved
 */
public class JuniorSwaggerConstant {

    public static ApiOperation defaultApiOperation = new ApiOperation() {

        @Override
        public Class<? extends Annotation> annotationType() {
            return ApiOperation.class;
        }

        @Override
        public String value() {
            return "";
        }

        @Override
        public String[] tags() {
            return new String[]{};
        }

        @Override
        public String[] produces() {
            return new String[]{};
        }

        @Override
        public RequestMethod[] methods() {
            return new RequestMethod[]{};
        }

        @Override
        public String summary() {
            return null;
        }

        @Override
        public boolean hidden() {
            return true;
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public String[] consumes() {
            return new String[]{};
        }
    };

}
