package com.yaoxj.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) {
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
        //bossgroup是一个线程池，线程数量默认是核心数*2，里面有个多NioEvetnLoop，每个NioEventLoop包含有一个selector和taskQueue
        EventLoopGroup workLoopGroup = new NioEventLoopGroup();
        //workgroop 有12个线程，当有客户端的进行消息处理的时候（即客户端进行write操作，服务端read操作），服务端的workgroup就场所一个线程进行处理，12-1.依次内推，知道12-12之后。再有
        //第13个客户端来的时候，就又从12-1开始


        try {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossLoopGroup,workLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ByteToLongDecode());
                            pipeline.addLast(new LongToByteEncoder());
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            //pipeline是是业务处理的管道，里面放着多个handler，channel是通道，获取数据的通道
            System.out.println("------服务端准备就绪---------------");

            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossLoopGroup.shutdownGracefully();
            workLoopGroup.shutdownGracefully();
        }

    }
}
