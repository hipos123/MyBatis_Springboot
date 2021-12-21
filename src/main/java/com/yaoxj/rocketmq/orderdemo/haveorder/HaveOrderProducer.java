package com.yaoxj.rocketmq.orderdemo.haveorder;

import com.yaoxj.rocketmq.orderdemo.Order;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-12-07 13:44
 **/
public class HaveOrderProducer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("haveGroupOrder");
        defaultMQProducer.setNamesrvAddr("192.168.0.195:9876");
        defaultMQProducer.start();
        try {


            List<Order> orderList=new ArrayList<>();
            Order order=new Order(1,"创建");
            orderList.add(order);
            order=new Order(2,"创建");
            orderList.add(order);
            order=new Order(2,"付款");
            orderList.add(order);

            order=new Order(3,"创建");
            orderList.add(order);
            order=new Order(1,"付款");
            orderList.add(order);
            order=new Order(1,"推送");
            orderList.add(order);

            order=new Order(3,"付款");
            orderList.add(order);
            order=new Order(3,"推送");
            orderList.add(order);
            order=new Order(1,"完成");
            orderList.add(order);
            order=new Order(2,"推送");
            orderList.add(order);

            order=new Order(2,"完成");
            orderList.add(order);
            order=new Order(3,"完成");
            orderList.add(order);

            //1，按照这个模式发送消息，获取到的消息是无序的
 /*           for (Order order1 : orderList) {
                Message message=new Message("order",order1.toString().getBytes());
                SendResult send = defaultMQProducer.send(message);
                System.out.println(send);
            }*/

            //有序消息
            for (Order order1 : orderList) {
                Message message=new Message("haveOrder",order1.toString().getBytes());
                SendResult send = defaultMQProducer.send(message, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                        int size = list.size();
                        int orderid=order1.getId();
                        int myorder= orderid % size;
                        return list.get(myorder);
                    }
                }, null);
                System.out.println(send);
            }


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
