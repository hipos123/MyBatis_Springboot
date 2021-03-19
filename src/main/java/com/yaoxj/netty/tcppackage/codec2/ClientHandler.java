package com.yaoxj.netty.tcppackage.codec2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-03-19 14:49
 **/
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i <10 ; i++) {
            ctx.writeAndFlush(new MyMessage(new MyHead(("abcd"+i).getBytes("UTF-8").length,1),"abcd"+i));
        }


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf in = (ByteBuf) msg;
        try {
            // Do something with msg
            System.out.println("client get :" + in.toString(CharsetUtil.UTF_8));

            ctx.close();
        } finally {
            //ByteBuf是一个引用计数对象，这个对象必须显示地调用release()方法来释放
            //or ((ByteBuf)msg).release();
            ReferenceCountUtil.release(msg);
        }
    }
}
