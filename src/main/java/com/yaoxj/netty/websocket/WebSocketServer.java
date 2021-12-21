package com.yaoxj.netty.websocket;

import com.yaoxj.netty.http.NettyChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-02-18 10:42
 **/
public class WebSocketServer {
    public static void main(String[] args) {
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
        //bossgroup是一个线程池，线程数量默认是核心数*2，里面有个多NioEvetnLoop，每个NioEventLoop包含有一个selector和taskQueue
        EventLoopGroup workLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossLoopGroup, workLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
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
                            pipeline.addLast(new MyTextFrameHandler());
                        }
                    });
            //sync是同步操作
            ChannelFuture channelFuture = serverBootstrap.bind(6669).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossLoopGroup.shutdownGracefully();
            workLoopGroup.shutdownGracefully();
        }
    }
}
