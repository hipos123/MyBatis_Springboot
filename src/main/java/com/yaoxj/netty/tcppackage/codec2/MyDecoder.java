package com.yaoxj.netty.tcppackage.codec2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-03-19 14:45
 **/
public class MyDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        int length = byteBuf.readInt();
        int version = byteBuf.readInt();

        byte[] body = new byte[length];
        byteBuf.readBytes(body);

        String content = new String(body, Charset.forName("UTF-8"));

        MyMessage myMessage = new MyMessage(new MyHead(length,version),content);

        list.add(myMessage);
    }
}