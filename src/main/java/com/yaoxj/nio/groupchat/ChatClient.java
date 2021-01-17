package com.yaoxj.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-01-14 15:12
 **/
public class ChatClient {
    private static final String HOST="127.0.0.1";
    private static final int PORT=6666;
    private Selector selector=null;
    private SocketChannel socketChannel=null;

    public ChatClient() throws IOException {
        selector=Selector.open();
        socketChannel=SocketChannel.open(new InetSocketAddress(HOST,PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void readInfo() throws IOException {
        int select = selector.select(2000);
        if(select>0){
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                SocketChannel channel = (SocketChannel) selectionKey.channel();
                ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
                channel.read(byteBuffer);
                System.out.println("从服务端读取到的数据是："+new String(byteBuffer.array()));
            }
        }
    }

    public void writeInfo2Server() throws IOException {
        String str=socketChannel.getRemoteAddress()+":hello,nio";
        ByteBuffer byteBuffer=ByteBuffer.wrap(str.getBytes());
        socketChannel.write(byteBuffer);
    }






}
