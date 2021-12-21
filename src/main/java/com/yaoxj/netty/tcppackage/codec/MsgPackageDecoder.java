package com.yaoxj.netty.tcppackage.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-03-18 15:39
 **/
public class MsgPackageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int len = in.readInt();
        byte[] bytes = new byte[len];
        in.readBytes(bytes);
        System.out.println(len + "decodeer===" + new String(bytes, CharsetUtil.UTF_8));
        ProtocolPkg protocolPkg = new ProtocolPkg();
        protocolPkg.setLength(len);
        protocolPkg.setContent(new String(bytes, CharsetUtil.UTF_8));
        out.add(protocolPkg);
    }
}
