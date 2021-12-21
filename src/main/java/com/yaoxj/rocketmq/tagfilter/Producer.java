package com.yaoxj.rocketmq.tagfilter;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-12-07 13:44
 **/
public class Producer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("groupFilter");
        defaultMQProducer.setNamesrvAddr("192.168.0.195:9876");
        defaultMQProducer.start();
        try {
            SendResult simple = defaultMQProducer.send(new Message("filter", "vip","123456789".getBytes()));
            System.out.println(simple);
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        defaultMQProducer.shutdown();


    }
}
