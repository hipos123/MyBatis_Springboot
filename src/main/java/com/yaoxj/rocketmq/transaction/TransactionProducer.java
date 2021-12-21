package com.yaoxj.rocketmq.transaction;

import com.yaoxj.rocketmq.orderdemo.Order;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-12-07 13:44
 **/
public class TransactionProducer {
    public static void main(String[] args) throws MQClientException {
        TransactionMQProducer transactionMQProducer = new TransactionMQProducer("tranGroup");

        transactionMQProducer.setNamesrvAddr("192.168.0.195:9876");
        transactionMQProducer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                //执行本地事务，把消息保存到数据库中 。发送half消息，等待broker返回
                System.out.println("===正常执行=========="+message.getBody());
                //只有这个正常执行消息成功之后，broker才会将消息放到队列中等待消费者消费
              /*  if(true){
                    return LocalTransactionState.COMMIT_MESSAGE;
                }else{
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }*/

                return LocalTransactionState.COMMIT_MESSAGE;

            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                //事务补偿过程, 根据补偿事务是否提交还是回滚状态broker来确认消息是否放到队列中。
                //一般情况下，从数据库查询一下消息是否已经存储，来判断是否提交还是回滚，正常执行过程要用LocalTransactionState.UNKNOW

                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        transactionMQProducer.start();

        Message message=new Message("tran","bigmall".getBytes());
        try {
            transactionMQProducer.send(message);
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
