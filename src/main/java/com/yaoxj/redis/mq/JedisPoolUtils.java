package com.yaoxj.redis.mq;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-10-19 14:40
 **/
public class JedisPoolUtils {


    private static JedisPool jedisPool;

    static {
        //读取配置文件
        InputStream is = JedisPoolUtils.class.getClassLoader().getResourceAsStream("jredis.properties");
        //创建Propertie对象
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
        config.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));

        //初始化Jedispool
        jedisPool = new JedisPool(config, properties.getProperty("host"), Integer.parseInt(properties.getProperty("port")));


    }

    /*获取连接的方法*/
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

}
