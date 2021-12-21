package com.yaoxj.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-02-04 10:27
 **/
public class HttpServerNettyMain {
    public static void main(String[] args) {
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
        //bossgroup是一个线程池，线程数量默认是核心数*2，里面有个多NioEvetnLoop，每个NioEventLoop包含有一个selector和taskQueue
        EventLoopGroup workLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossLoopGroup, workLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyChannelInitializer());
            //sync是同步操作
            ChannelFuture channelFuture = serverBootstrap.bind(6669).sync();
      /*      ChannelFuture channelFuture = serverBootstrap.bind(6668);
            //异步模型
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isDone()){
                        if(channelFuture.isSuccess()){
                            System.out.println("执行成功...");
                        }else if(channelFuture.isCancelled()){
                            System.out.println("任务被取消...");
                        }else if(channelFuture.cause()!=null){
                            System.out.println("执行出错："+channelFuture.cause().getMessage());
                        }
                    }
                }
            });*/
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossLoopGroup.shutdownGracefully();
            workLoopGroup.shutdownGracefully();
        }

    }

}
