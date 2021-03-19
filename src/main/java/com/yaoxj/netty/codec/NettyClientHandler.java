package com.yaoxj.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class NettyClientHandler  extends SimpleChannelInboundHandler<Long> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        System.out.println("发送消息给服务端--------");
//        ctx.writeAndFlush("abcdabcdabcdabcdabcd"); 如果这边传的是字符串过去，类型不匹配。服务端是收不到数据的，客户端也不会发出去
        ctx.writeAndFlush(123456789l);

    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("获取到服务端发送回来的数据："+msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        System.out.println("出现了异常----》"+cause.getMessage());
        ctx.close();

    }
}
