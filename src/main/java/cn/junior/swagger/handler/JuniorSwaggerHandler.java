package cn.junior.swagger.handler;

import com.jfinal.handler.Handler;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import cn.junior.swagger.utils.StringUtil;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Title: JuniorSwaggerHandler
 * @Description:
 * @Author:Junior Ray
 * @Date: 2020/7/15 4:16 PM
 * @Version 1.0
 * @Copyright: Copyright (c) 2020 SoftSz All rights reserved
 */
public class JuniorSwaggerHandler extends Handler {
    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        String path = null;
        if (target.contains("doc.html")) {
            path = "doc.html";
        } else if (target.startsWith("/webjars/")) {
            path = "webjars/" + StringUtil.substringAfter(target, "/webjars/");
        } else if (target.startsWith("/WebJars/")) {
            path = "WebJars/" + StringUtil.substringAfter(target, "/WebJars/");
        }
        if (StrKit.notBlank(path)) {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
            OutputStream outputStream = null;
            try {
                if (inputStream != null) {
                    outputStream = response.getOutputStream();
                    IOUtils.copy(inputStream, response.getOutputStream());
                }
            } catch (IOException e) {
                LogKit.error("cant get static resource : " + path + " from jar", e);
            } finally {
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(outputStream);
            }
            isHandled[0] = true;
        }
        this.next.handle(target, request, response, isHandled);
    }


}
