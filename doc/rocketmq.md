##rocketmq，nameserver 
>  nameserver 命名服务,主要用于维护和保存整个集群中的元数据信息。
>  集群中的所有broker，producer和consumer机器都要和nameServer进行长链接，用于请求或发送数据。
>
>broker cluster 注册到namserver中。producer向nameServe-cluster中发送信息获取broker信息
>，nameServer-cluster返回broker的ip。然后producer向这个broker-ip发送数据。这个才是正式的发送数据
>同理，consumer也需要向nameserver中发送信息，来获取broker的ip，拿到ip之后才
>正式向broker-ip拉取数据。

##rocketmq，message
>消息：message 主题：topic 标题：tag 
>
##RocketMQ Dashboard
> 也就是说之前的rocket-console已经不在这个仓库了，已经创建了一个独立的仓库并命名为RocketMQ Dashboard
> https://github.com/apache/rocketmq-dashboard
##RocketMQ 异常
>1. 连接nameserver的时候报超时，可以关闭掉防火墙
>2. systemctl stop firewalld
>3. systemctl status  firewalld

##一个生产者，多个消费者
>1. 消费者同一个组，默认情况下，负载均衡，平均消费生产者发送的消息。
>2. 消费者不同的组，默认情况下，各个消费者全部消费生产者发送的消息。
>3. 消费者同一个，设置defaultMQPushConsumer.setMessageModel(MessageModel.BROADCASTING);
默认情况下设置得是CLUSTERING ，这种模式是负载均衡模式
>
##消息类型
>1. 同步消息：及时性较强，重要的消息，且必须有回执的消息，例如短信，通知（转账成功）
>2. 异步消息：及时性较弱，但是需要回执的消息，例如订单中的某些信息。
>3. 单向消息：不需要回执的消息，例如日志类消息
> defaultMQProducer.sendOneway();
>4. 延时消息：不需要回执的消息，例如日志类消息
>5. 批量消息：
> List<Message> msgList=new ArrayList<>();
> SendResult send = defaultMQProducer.send(msgList);
>批量消息应该有相同的topic，不能是延时消息，消息内容不能超过4m 必须是相同的waitStoreMsgOk
>
##tag过滤
SendResult simple = defaultMQProducer.send(new Message("filter", "vip","123456789".getBytes()));

defaultMQPushConsumer.subscribe("filter","tag1 || vip");

##sql过滤
Message message = new Message("sqlfilter", "vip", "this is age".getBytes());
message.putUserProperty("username","yaoxj");
message.putUserProperty("age","17");
SendResult simple = defaultMQProducer.send(message);
            
defaultMQPushConsumer.subscribe("sqlfilter", MessageSelector.bySql("age>15"));


##springboot模式下的消息类型和消息过滤   springboot项目在RuoYi-fast

```
//同步消息，直接就有返回值
SendResult syncTopic = rocketMQTemplate.syncSend("syncTopic:tag1||tag2", rocketPojo);

//异步消息，有个回调
rocketMQTemplate.asyncSend("asyncTopic", rocketPojo, new SendCallback() {
    @Override
    public void onSuccess(SendResult sendResult) {

    }

    @Override
    public void onException(Throwable throwable) {

    }
});

//单向消息
rocketMQTemplate.sendOneWay("onewayTopic",rocketPojo);

//延迟消息
rocketMQTemplate.syncSend("delayTopic",
        MessageBuilder.withPayload(rocketPojo).build(),200,2);


//消费者接受消息
//@RocketMQMessageListener(topic = "bootMqTopic",consumerGroup = "mqgroup",selectorExpression = "tag1")
@RocketMQMessageListener(topic = "bootMqTopic",consumerGroup = "mqgroup",
        selectorType= SelectorType.SQL92,selectorExpression = " age>10 ",
        messageModel= MessageModel.BROADCASTING)
```
##消息顺序 springboot项目在RuoYi-fast
>1. 队列内有序，队列外无需
>2. 默认生产4个队列，会产生消息错乱，所以把这一批消息放到同一个队列中，按队列原则，先进先出处理，保证有序
```
https://blog.csdn.net/qq_31289187/article/details/105978280
//有序消息  hashKey是传入的订单ID，然后对订单ID计算hashCode值，然后对hashCode取模，返回对应队列。

//同一个订单在同一个队列中
rocketMQTemplate.syncSendOrderly("orderTopic",rocketPojo,rocketPojo.getOrderId()+"",3000);

@RocketMQMessageListener(topic = "order",consumerGroup = "mygroup",
consumeMode = ConsumeMode.ORDERLY)

```
##消息事务
>1. 事务消息只和生产者有关系，和消费者无关
```
TransactionMQProducer transactionMQProducer = new TransactionMQProducer("tranGroup");
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

 transactionMQProducer.send(message);

```
##高级特性——消息的存储
producer ----1，发送消息---------->    broker   ------3.发送消息到消费者--------》consumer

producer <---2，返回消息，代表broker收到了消息----- broker <----4.消费者收到消息--------------- consumer

正常情况下，生产者或者消费者收到消息之后，都有一个ack的操作。让broker确认是否已经收到消息或者消息是否都已经发送成功。
但是这边存在一个问题，就是broker如果挂掉了，就会导致生产者重复发送消息，或者消费者重复接受到消息
所以这个时候消息到了broker之后就得存储起来。后面根据存储的数据再来判断是否要发送消息。
存储消息的介质有数据库和文件。直接操作文件效率更高。

##高级特性——高速读写(申请大空间存储，顺序读取;零拷贝)
>1. rocketmq一次性申请2g的存储空间（可以配置），获取到的消息按顺序放进去，顺序读写，非常快。
>2. 正常情况下消息从内核态---》用户态----》网卡，发送消息出去。
>  现在rocketmq采用零拷贝技术，直接省略掉了
> 用户态，直接从内核态-----》网卡，省略了消息的拷贝过程。

##高级特性——消息存储结构
>1. 消息存储区 （commitlog） 就是一个个MessageQueue ：topic，ququqid，message
>2. 消费逻辑队列 （consumerqueue）包含：  minOffset maxOffSet consumerOffset
>3. 索引（index）

##高级特性——刷盘机制
>1. 同步刷盘：生产者发送消息到broker，broker收到消息后挂起生产者发送消息的线程
>   broker将消息写入到硬盘。硬盘存储成功之后返回success，broker恢复挂起的生产者线程。

>2. 异步刷盘：生产者不断的发消息给broker，broker积累了一些消息之后，一次性存储dao
>磁盘，或者过了几秒钟之后，一次性存储到磁盘。不要每次都收到消息就存储。
flushDiskType=ASYNC_FLUSH (异步刷盘)/ SYNC_FLUSH （同步刷盘） 

##高级特性——负载均衡
>1. producer负载均衡
>2. consumer负载均衡

##高级特性——消息重试
//同步发送消息，如果5秒内没有发送成功，则重试5次
DefaultMQProducer producer = new DefaultMQProducer("DefaultProducer");
producer.setRetryTimesWhenSendFailed(5);
producer.send(msg,5000L);

//抛出异常时，返回ConsumeConcurrentlyStatus.RECONSUME_LATER，尝试重试。当重试指定次数后返回ConsumeConcurrentlyStatus.CONSUME_SUCCESS
   int reconsumeTimes = message.getReconsumeTimes();
   System.err.println("Now Retry Times: " + reconsumeTimes);
   if (reconsumeTimes >= RocketMQConstant.MAX_RETRY_TIMES) {
       return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
   }

>1.顺序消息，broker会自动重试
>2.无序消息，broker会不断重试，间隔越来越长，最多16次

当mq重试16次之后，broker就不会继续向消费者发送消息，变成死信消息。

死信队列中消息不会被再次重复消息。死信队列中的消息有效期为3天，达到时限后将被清除

##高级特性——消息重复消费
