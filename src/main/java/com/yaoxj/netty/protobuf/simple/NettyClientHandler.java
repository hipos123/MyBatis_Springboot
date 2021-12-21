package com.yaoxj.netty.protobuf.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        System.out.println("发送消息给服务端--------");
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(32).setName("tttyyuu").build();
        ctx.writeAndFlush(student);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student student) throws Exception {
        System.out.println("id==" + student.getId() + "****name===" + student.getName());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        System.out.println("出现了异常----》" + cause.getMessage());
        ctx.close();

    }
}
