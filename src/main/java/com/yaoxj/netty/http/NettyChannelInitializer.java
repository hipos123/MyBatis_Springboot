package com.yaoxj.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-02-04 11:12
 **/
public class NettyChannelInitializer  extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //可以将channel放到缓存中，这个socketChannel就是客户端的channel
        System.out.println("socketChannel hashcode====="+socketChannel.hashCode());
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("myHttpServerCode",new HttpServerCodec());
        pipeline.addLast("httpAggregator",new HttpObjectAggregator(512*1024)); // http 消息聚合器
        pipeline.addLast("myHandler",new MyNettyHttpServerHandler());
    }
}
