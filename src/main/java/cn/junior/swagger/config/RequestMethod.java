package cn.junior.swagger.config;

/**
 * @Title: RequestMethod
 * @Description:
 * @Author:Junior Ray
 * @Date: 2020/7/15 4:16 PM
 * @Version 1.0
 * @Copyright: Copyright (c) 2020 SoftSz All rights reserved
 */
public enum RequestMethod {

    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

    private static RequestMethod[] all = {GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE};

    public static RequestMethod[] all() {
        return all;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
