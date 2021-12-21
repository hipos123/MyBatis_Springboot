package com.yaoxj.netty.tcppackage.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-03-18 15:32
 **/
public class MsgPackegeEncoder extends MessageToByteEncoder<ProtocolPkg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ProtocolPkg msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getLength());
        out.writeBytes(Unpooled.copiedBuffer(msg.getContent(), CharsetUtil.UTF_8));
    }
}
