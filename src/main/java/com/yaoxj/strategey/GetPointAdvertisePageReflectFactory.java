package com.yaoxj.strategey;

import cn.hutool.core.lang.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-08-24 15:22
 **/
@Component
public class GetPointAdvertisePageReflectFactory {
    @Autowired
    private ApplicationContext context;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetPointAdvertisePageReflectFactory.class);
    // 通过配置文件名：filePathName   和 文件中的key 得到指定的类
    public GetPointAdvertisePageStrategey getAdvertisePageStrategey(String filePathName, String key){
        GetPointAdvertisePageStrategey getPointAdvertisePageStrategey = null;
        String classPath = null;
        try {
            classPath = PropertyUtil.get(filePathName,key);
            if(Validator.isNull(classPath)) return null;
            //通过反射创建对象
            Class<?> handler = Class.forName(classPath);
            //这里必须强行纳入spring管理，否则在得到的具体实现类中，如果有通过@Autowired注入的bean，将会报注入失败
            getPointAdvertisePageStrategey = (GetPointAdvertisePageStrategey) context.getAutowireCapableBeanFactory().createBean(handler, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
        } catch (Exception e) {
            LOGGER.error("Failed to reflect the corresponding object through the specified path，filePath:{},key:{}",filePathName,key);
            return null;
        }
        return getPointAdvertisePageStrategey;
    }
}
