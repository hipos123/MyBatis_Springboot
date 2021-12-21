package com.yaoxj.netty.tcppackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class NettyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        System.out.println("服务端发生了异常:--->" + cause.getMessage());
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] barray = new byte[msg.readableBytes()];
        //把数据从bytebuf转移到byte[]
        msg.getBytes(0, barray);
        //将byte[]转成字符串用于打印
        String str = new String(barray, CharsetUtil.UTF_8);
        System.out.println("获取到客户端数据：" + str);
        ByteBuf byteBuf = Unpooled.copiedBuffer("我是服务端" + (++count), CharsetUtil.UTF_8);
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("我是服务端" + (++count));
    }
}
