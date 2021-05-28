package com.yaoxj.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        Selector selector = Selector.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        serverSocketChannel.configureBlocking(false);
        //注册到selector上，第二个参数是注册的事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(true){
            //选择器的select是阻塞事件，等待1秒，是否有事件发，如果没有事件发生，可以做自己的事情
            if (selector.select(1000)==0) {
//                System.out.println("服务器等待了1秒，无事件发生");
                continue;
            }
            System.out.println("当前有多个事件："+selector.keys().size()+
                    "&&&thread Name==="+Thread.currentThread().getName()+"&&&Thread id==="+Thread.currentThread().getId());


            //selectionKeys 事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //根据key ，查看通道发生的事情
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel= serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("服务端接收到了客户端的连接:"+socketChannel.hashCode());
                }

                if(selectionKey.isReadable()){
                    SocketChannel socketChannel= (SocketChannel) selectionKey.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    socketChannel.read(byteBuffer);
                    System.out.println(socketChannel.hashCode()+"服务端获取到的数据是："+new String(byteBuffer.array()));
                }
                iterator.remove();
            }

            Set<SelectionKey> keys = selector.keys();
            for(SelectionKey key:keys){
                Channel channel = key.channel();//通过selectionKey反向获取channel
                System.out.println("key.channel():"+channel.hashCode());
            }



        }

    }

}
