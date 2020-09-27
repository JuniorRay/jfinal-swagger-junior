package cn.junior.swagger.controller;

import cn.junior.swagger.config.JuniorSwaggerPlugin;
import cn.junior.swagger.model.SwaggerDoc;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PathKit;

import java.io.File;

/**
 * @Title: JuniorSwaggerController
 * @Description:
 * @Author:Junior Ray
 * @Date: 2020/7/15 4:16 PM
 * @Version 1.0
 * @Copyright: Copyright (c) 2020 SoftSz All rights reserved
 */
@Clear
public class JuniorSwaggerController extends Controller {

    public static final String API_URL = "swagger/api";

    public void index() {
        render("doc.html");
    }

    public static void main(String[] args) throws Exception{
        String path = PathKit.class.getClassLoader().getResource("").toURI().getPath();
        File file = new File(path+"doc.html");
        System.out.println(file.exists());
    }
    public void api() {
        SwaggerDoc swaggerDoc = JuniorSwaggerPlugin.getDoc(getPara("basePackage"));
        renderJson(swaggerDoc == null ? "" : JsonKit.toJson(swaggerDoc));
    }

    public void swagger_resources() {
        renderJson(JsonKit.toJson(JuniorSwaggerPlugin.getApiInfo()));
    }

}