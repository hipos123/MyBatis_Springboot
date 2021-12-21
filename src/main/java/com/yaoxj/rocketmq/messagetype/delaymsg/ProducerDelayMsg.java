package com.yaoxj.rocketmq.messagetype.delaymsg;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @description:同步消息，等着返回的就是同步消息，默认的这种设置
 * @author: yaoxj
 * @create: 2021-12-07 13:44
 **/
public class ProducerDelayMsg {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("group5");
        defaultMQProducer.setNamesrvAddr("192.168.0.195:9876");
        defaultMQProducer.start();
        try {
            for (int i = 0; i < 10; i++) {
                Message model = new Message("model", ("hello" + i).getBytes());
                model.setDelayTimeLevel(3);
                defaultMQProducer.send(model);
            }
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }
        defaultMQProducer.shutdown();


    }
}
