package com.yaoxj.rocketmq.messagetype.async;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @description:异步消息
 * @author: yaoxj
 * @create: 2021-12-07 13:44
 **/
public class ProducerMessageAsyncType {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("group5");
        defaultMQProducer.setNamesrvAddr("192.168.0.195:9876");
        defaultMQProducer.start();
        try {
            for (int i = 0; i < 10; i++) {
                Message model = new Message("model", ("hello" + i).getBytes());
                defaultMQProducer.send(model, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {

                    }

                    @Override
                    public void onException(Throwable throwable) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        defaultMQProducer.shutdown();

    }


}
