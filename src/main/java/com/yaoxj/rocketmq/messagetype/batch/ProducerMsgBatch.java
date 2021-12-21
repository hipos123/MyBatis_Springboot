package com.yaoxj.rocketmq.messagetype.batch;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:同步消息，等着返回的就是同步消息，默认的这种设置
 * @author: yaoxj
 * @create: 2021-12-07 13:44
 **/
public class ProducerMsgBatch {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("group10");
        defaultMQProducer.setNamesrvAddr("192.168.0.195:9876");
        defaultMQProducer.start();

        List<Message> msgList=new ArrayList<>();
        Message msg1 = new Message("batch", "batchTags",("hello micu msg1").getBytes());
        msgList.add(msg1);

        Message msg2 = new Message("batch", "batchTags",("hello micu msg2").getBytes());
        msgList.add(msg2);

        Message msg3 = new Message("batch","batchTags", ("hello micu msg3").getBytes());
        msgList.add(msg3);

        try {
            SendResult send = defaultMQProducer.send(msgList);
            System.out.println(send);
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
