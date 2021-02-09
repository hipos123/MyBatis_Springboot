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
