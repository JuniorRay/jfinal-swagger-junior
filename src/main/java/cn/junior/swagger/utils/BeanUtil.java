package cn.junior.swagger.utils;

import com.jfinal.plugin.activerecord.Model;

import java.lang.reflect.Field;
/**
 * @Title: BeanUtil
 * @Description:
 * @Author:Junior Ray
 * @Date: 2020/7/15 4:16 PM
 * @Version 1.0
 * @Copyright: Copyright (c) 2020 SoftSz All rights reserved
 */
public class BeanUtil {

    /**
     * 拷贝Model参数到对象
     *
     * @param src    数据源Model
     * @param target 目标对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T copyParameter(Model src, Object target) {
        Class thisClass = target.getClass();
        Field[] fields = thisClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = src.get(field.getName());
                if (value == null) {
                    //驼峰没取到就取下划线
                    field.set(target, src.get(StringUtil.underscoreName(field.getName())));
                } else {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (T) target;
    }

}
