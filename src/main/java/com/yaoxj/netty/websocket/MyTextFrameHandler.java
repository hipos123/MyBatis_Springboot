package com.yaoxj.netty.websocket;

import com.sun.xml.internal.ws.handler.HandlerException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;

import java.util.Date;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-02-18 11:52
 **/
public class MyTextFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    //客户端连接的时候，触发这个方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //asLongText 长id是唯一的。asShortText 短id。不是唯一的
        System.out.println("handlerAdd 被调用"+ctx.channel().id().asLongText());
        System.out.println("handlerAdd 被调用"+ctx.channel().id().asShortText());
    }

    //断开连接的时候，会主动触发这个方法，自动移除
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved 被调用"+ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生了异常"+cause.getMessage());
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {

        Channel channel = ctx.channel();
        // region 判断是否是关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            System.out.println("├ 关闭与客户端["+ channel.remoteAddress()+"]链接");
//            socketServerHandShaker.close(channel, (CloseWebSocketFrame) frame.retain());
            return;
        }
        // endregion
        // region 判断是否是ping消息
        if (frame instanceof PingWebSocketFrame) {
            System.out.println(("├ [Ping消息]"));
            channel.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // endregion
        // region 纯文本消息
        if (frame instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) frame).text();
            System.out.println("├ ["+new Date()+" 接收到客户端的消息]: "+text );
            channel.writeAndFlush(new TextWebSocketFrame(new Date() + " 服务器将你发的消息原样返回：" + text));
        }
        // endregion
        // region 二进制消息 此处使用了MessagePack编解码方式
 /*       if (frame instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) frame;
            ByteBuf content = binaryWebSocketFrame.content();
            System.out.println(("├ [二进制数据]:{}", content));
            final int length = content.readableBytes();
            final byte[] array = new byte[length];
            content.getBytes(content.readerIndex(), array, 0, length);
            MessagePack messagePack = new MessagePack();
            WebSocketMessageEntity webSocketMessageEntity = messagePack.read(array, WebSocketMessageEntity.class);
            System.out.println(("├ [解码数据]: {}", webSocketMessageEntity));
            WebSocketUsers.sendMessageToUser(webSocketMessageEntity.getAcceptName(), webSocketMessageEntity.getContent());
        }*/
        // endregion

    }
}
