package com.yaoxj.redis;

import ch.qos.logback.core.net.SyslogOutputStream;
import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.yaoxj.redis.mq.JedisPoolUtils;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-10-19 14:36
 **/
public class SimpleRedis {
    public static final String CHANNEL_KEY = "channel:1";
    private volatile int count;

    public void putMessage(String message) {
        Jedis jedis = JedisPoolUtils.getJedis();
        Long publish = jedis.publish(CHANNEL_KEY, message);//返回订阅者数量
        System.out.println(Thread.currentThread().getName() + " put message,count=" + count + ",subscriberNum=" + publish);
        count++;
    }


    public static void main(String[] args) throws InterruptedException {

        Jedis jedis = JedisPoolUtils.getJedis();
        String currentIsValid = "6f97c83a2156bb6334ced8d0d994baf6" + StrUtil.COLON + "isExp" + StrUtil.COLON + "2021-12-08";
//        ::2021-12-08
        jedis.set(currentIsValid, "true");

        String isExpToday = jedis.get(currentIsValid);
        System.out.println(isExpToday);
        if (!StringUtil.isEmpty(isExpToday)) {
            if ("true".equals(isExpToday)) {
                System.out.println("true");
            } else {
                System.out.println("false");
            }
        }
    }
}
