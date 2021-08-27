package com.yaoxj.strategey;

import cn.hutool.core.lang.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-08-24 15:23
 **/
public class PropertyUtil {
    private static Map<String, Properties> cache = new HashMap<>();

    /**
     *
     * @param configFileName 配置文件名
     * @param key  配置文件中的 key=value  中的key
     * @return
     */
    public static String get(String configFileName,String key){

        ResourceBundle resource = ResourceBundle.getBundle(configFileName);
        String val = resource.getString(key);
//        System.out.println("key======="+key);
//        return getProperties(configFileName).getProperty(key);
        return val;
    }

    public static Properties getProperties(String configFileName) {
        if (Validator.isNotNull(cache.get(configFileName))) {
            return cache.get(configFileName);
        }
        InputStream inputStream = PropertyUtil.class.getResourceAsStream(configFileName);
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cache.put(configFileName,properties);
        return  properties;
    }
}
