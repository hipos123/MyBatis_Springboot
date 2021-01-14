package com.yaoxj.nio.groupchat;

import org.apache.ibatis.annotations.SelectKey;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ChatServer {
    private static final  int port=6666;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public ChatServer() throws IOException {
        serverSocketChannel=ServerSocketChannel.open();
        selector=Selector.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void AcceptClientConnect() throws IOException {
        while(true){
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if(selectionKey.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    System.out.println(socketChannel.getRemoteAddress()+"上线了");
                }
            }
        }
    }

    public  void sendMsg2OtherChannel(String msg,SocketChannel self){

    }
}
