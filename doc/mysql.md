##mysql复制原理
>1. master库的io线程复制二进制文件bin-log到salve库的relay-log中继日志
>2. salve库的sql线程执行relay-log将sql刷入到salve库中
##mysql为什么需要主从同步？
>1.为了读写分离
>2.做数据的热备
>3.单机无法满足，多库存储，降低磁盘io访问的频率
