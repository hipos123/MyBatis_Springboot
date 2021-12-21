package com.yaoxj.redis.mq;

import redis.clients.jedis.Jedis;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-10-19 14:36
 **/
public class MessageProducer extends Thread {
    public static final String CHANNEL_KEY = "channel:1";
    private volatile int count;

    public void putMessage(String message) {
        Jedis jedis = JedisPoolUtils.getJedis();
        Long publish = jedis.publish(CHANNEL_KEY, message);//返回订阅者数量
        System.out.println(Thread.currentThread().getName() + " put message,count=" + count + ",subscriberNum=" + publish);
        count++;
    }

    @Override
    public synchronized void run() {
        for (int i = 0; i < 5; i++) {
            putMessage("message" + count);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MessageProducer messageProducer = new MessageProducer();
        Thread t1 = new Thread(messageProducer, "thread1");
        Thread t2 = new Thread(messageProducer, "thread2");
        Thread t3 = new Thread(messageProducer, "thread3");
        Thread t4 = new Thread(messageProducer, "thread4");
        Thread t5 = new Thread(messageProducer, "thread5");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        sleep(10000);
        messageProducer.putMessage("exit");
    }
}
