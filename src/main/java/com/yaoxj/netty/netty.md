Server 端包含 1 个 Boss NioEventLoopGroup 和 1 个 Worker NioEventLoopGroup。

NioEventLoopGroup 相当于 1 个事件循环组，这个组里包含多个事件循环 NioEventLoop，每个 NioEventLoop 包含 1 个 Selector 和 1 个事件循环线程。

每个 Boss NioEventLoop 循环执行的任务包含 3 步：

1）轮询 Accept 事件；

2）处理 Accept I/O 事件，与 Client 建立连接，生成 NioSocketChannel，并将 NioSocketChannel 注册到某个 Worker NioEventLoop 的 Selector 上；

3）处理任务队列中的任务，runAllTasks。任务队
列中的任务包括用户调用 eventloop.execute 或 schedule 执行的任务，或者其他线程提交到该 eventloop 的任务。

每个 Worker NioEventLoop 循环执行的任务包含 3 步：

1）轮询 Read、Write 事件；

2）处理 I/O 事件，即 Read、Write 事件，在 NioSocketChannel 可读、可写事件发生时进行处理；

3）处理任务队列中的任务，runAllTasks。

###netty
1. bossgroup是一个线程池，线程数量默认是核心数*2，里面有个多NioEvetnLoop，每个NioEventLoop包含有一个selector和taskQueue
2. workgroop 有12个线程，当有客户端的进行消息处理的时候（即客户端进行write操作，服务端read操作），
   服务端的workgroup就场所一个线程进行处理，第13个客户端来的时候，就又从12-1开始
3. pipeline是是业务处理的管道，里面放着多个handler，channel是通道，获取数据的通道，
   pipeline可以获取channel，channel可以获取放在哪一个pipeline里面
4.  ch.pipeline().addLast(new NettyServerHandler());
5. 如果这个业务操作很耗时，放在主线程上做的话，会影响其他的操作，所以在这种情况下，将耗时的业务操作放在任务队列中执行，异步处理
 ctx.channel().eventLoop().execute或者ctx.channel().eventLoop().schedule

###unpooled 
netty提供了一个专门用来操作缓冲区（即netty的数据容器）的工具类Unpooled。{nio提供的是ByteBuffer
netty用的是ByteBuf} ，常用的方法有：
>Unpooled.buffer(10);
>Unpooled.copiedBuffer("hello yaoxj", CharsetUtil.UTF_8);
>System.out.println(byteBufStr.arrayOffset());
>System.out.println(byteBufStr.readerIndex());
>byteBufStr.writerIndex()
>byteBufStr.capacity()
>byteBufStr.getCharSequence(0,4,CharsetUtil.UTF_8)
###heartBeat 心跳检测
1. IdleStateHandler 是netty提供的空闲状态的处理器
2. long readerIdleTime:表示多长时间没有读，就会发送一个心跳检测包检测是否连接
3. long writerIdleTime：表示多长时间没有写，就会发送一个心跳检测包检测是否连接
4. long allIdleTime：表示多长时间么有读写，就会发送一个心跳检测包检测是否连接
5. 当空闲检测触发之后，就会传递给管道的下一个handler，通过下一个handler的userEventTriggered方法
来处理读空闲，写空闲或者读写空闲
6. pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
7. 加入一个对空闲检测进一步处理的handler
8. pipeline.addLast(new MyIdleStateHandler());

###websocket长连接
1. websocket使用了一部分http的协议，但是他自己是一个独立的协议，和http协议没有关系。
http是无状态的协议，都是一个次请求，一次相应。如果还有请求，就需要不断的和服务器建立连接，这个开销需要
耗费非常多的资源。还有一点，服务端有消息都是需要客户端主动来轮询的，服务端不会主动推送消息给客户端。

2. websocket的优势在于客户端和服务端只要进行一次的连接，就可以不断的收发消息，不要进行其他的连接操作。
服务端也可以主动的推送消息给客户端。

`ChannelPipeline pipeline = ch.pipeline();
//因为基于http协议，使用http的编码和解码器
pipeline.addLast(new HttpServerCodec());
//是以块的方式写，所以需要ChunkedWriteHandler处理器
pipeline.addLast(new ChunkedWriteHandler());
//http在传输过程中是分段的，HttpObjectAggregator可以将多个段聚合发送,y有的时候在浏览器看到多个请求
//但是服务端只有一份聚合的数据
pipeline.addLast(new HttpObjectAggregator(8192));
//对于websocket，数据是以帧（frame）的形式传递
//可以看到websocketFrame下面有6个子类
//浏览器请求时 ws://localhost:6669/hello，表示请求的uri
//WebSocketServerProtocolHandler核心的功能就是将http协议升级为ws协议，保持长连接
pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
pipeline.addLast(new MyTextFrameHandler());`

###protobuf 编码解码器
1. netty提供了很多编码和解码器比如：
StringEncoder  -----------》StringDecoder  :对字符串进行编码解码
ObjectEncoder  -----------》ObjectDecoder  :对对象进行编码解码
ObjectEncoder和ObjectDecoder是对对象进行编码和解码操作，底层使用的仍然是java序列化技术，而java
序列化技术本身效率就不高，存在的以下问题：
1）无法跨语言
2）序列化之后体积太大，是二进制编码的5倍多
3）序列化性能太低
所以引进了Google的protobuf技术。

###protobuf 开发步骤
1. 创建protobuf文件：Student.proto
2. 使用protobuf工具创建java文件: protoc.exe --java_out=. Student.proto
生成StudentPOJO.java文件
3. server端和client 增加编码解码器：
 pipeline.addLast("protobufVarint32FrameDecoder", new ProtobufVarint32FrameDecoder());
 pipeline.addLast("protobufDecoder", new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
 pipeline.addLast("protobufVarint32LengthFieldPrepender", new ProtobufVarint32LengthFieldPrepender());
 pipeline.addLast("protobufEncoder", new ProtobufEncoder());








