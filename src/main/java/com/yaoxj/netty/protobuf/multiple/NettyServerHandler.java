package com.yaoxj.netty.protobuf.multiple;

import com.yaoxj.netty.protobuf.simple.StudentPOJO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerHandler extends SimpleChannelInboundHandler<DataInfoPOJO.DataPackage> {



    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        System.out.println("服务端发生了异常:--->"+cause.getMessage());
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfoPOJO.DataPackage msg) throws Exception {
        System.out.println("msg.getPackageType().getNumber()：" + msg.getPackageType().getNumber());
        switch (msg.getPackageType().getNumber()) {
            case 0:
                System.out.println(msg.getSudent().getName());
                System.out.println(msg.getSudent().getAge());
                System.out.println(msg.getSudent().getAddress());
                break;
            case 1:
                System.out.println(msg.getDog().getDogName());
                System.out.println(msg.getDog().getDogAge());
                break;
        }

    }


}
