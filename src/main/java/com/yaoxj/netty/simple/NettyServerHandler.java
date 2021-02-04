package com.yaoxj.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
//        ByteBuf buf= (ByteBuf) msg;
//        System.out.println("读取到客户端发来的消息======"+buf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端的地址是："+ctx.channel().remoteAddress());

        try {
            ByteBuf buf = (ByteBuf)msg;
            //创建目标大小的数组
            byte[] barray = new byte[buf.readableBytes()];
            //把数据从bytebuf转移到byte[]
            buf.getBytes(0,barray);
            //将byte[]转成字符串用于打印
            String str=new String(barray);

            if (str.length()>0)
            {
                System.out.println("读取到客户端发来的消息======"+str);
                System.out.flush();
            }
            else
            {
                System.out.println("不能读啊");
            }
            buf.release();
        }finally {
            //buf.release();
        }
    //如果这个业务操作很耗时，放在主线程上做的话，会影响其他的操作，所以在这种情况下，将耗时的业务操作放在任务队列中执行，异步处理

//        Thread.sleep(10*1000);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("延迟10秒发消息给你",CharsetUtil.UTF_8));
        // 处理任务队列中的任务，runAllTasks
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10*1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("延迟10秒发消息给你",CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("-------异步操作，不会阻塞在这边------------------");
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5*1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("延迟5秒发消息给你,schedule",CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },5, TimeUnit.SECONDS);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端，我是服务端",CharsetUtil.UTF_8));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        System.out.println("服务端发生了异常:--->"+cause.getMessage());
        ctx.close();
    }
}
