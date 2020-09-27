package cn.junior.swagger.routes;

import cn.junior.swagger.controller.JuniorSwaggerController;
import com.jfinal.config.Routes;

/**
 * @Title: JuniorSwaggerRoutes
 * @Description:
 * @Author:Junior Ray
 * @Date: 2020/7/15 4:16 PM
 * @Version 1.0
 * @Copyright: Copyright (c) 2020 SoftSz All rights reserved
 */
public class JuniorSwaggerRoutes extends Routes {
    @Override
    public void config() {
        add("/swagger", JuniorSwaggerController.class, "/");
    }
}
