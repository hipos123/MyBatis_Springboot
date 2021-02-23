package com.yaoxj.netty.protobuf.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {



    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端，我是服务端",CharsetUtil.UTF_8));
        StudentPOJO.Student student= StudentPOJO.Student.newBuilder().setId(666).setName("我是服务端").build();
        ctx.writeAndFlush(student);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        System.out.println("服务端发生了异常:--->"+cause.getMessage());
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student student) throws Exception {
        System.out.println("id=="+student.getId()+"****name==="+student.getName());
    }


}
