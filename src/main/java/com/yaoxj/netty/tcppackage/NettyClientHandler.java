package com.yaoxj.netty.tcppackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class NettyClientHandler  extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        System.out.println("发送消息给服务端--------");
        for (int i = 0; i <10 ; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer("你好，服务端"+i,CharsetUtil.UTF_8));
        }

    }

/*    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        ByteBuf buf= (ByteBuf) msg;
        System.out.println("获取到服务端发送回来的数据："+buf.toString(CharsetUtil.UTF_8));
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("获取到服务端发送回来的数据："+msg.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        System.out.println("出现了异常----》"+cause.getMessage());
        ctx.close();

    }
}
