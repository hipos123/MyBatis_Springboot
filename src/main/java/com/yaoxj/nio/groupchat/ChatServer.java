package com.yaoxj.nio.groupchat;

import org.apache.ibatis.annotations.SelectKey;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ChatServer {
    private static final int port = 6666;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public ChatServer() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        selector = Selector.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void AcceptClientConnect() throws IOException {
        while (true) {
            int select = selector.select();
            if (select > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println(socketChannel.getRemoteAddress() + "上线了");
                    }

                    if (selectionKey.isReadable()) {
                        readMsgFromClient();
                    }
                    iterator.remove();
                }
            }
        }
    }

    public void sendMsg2OtherChannel(String msg, SocketChannel self) throws IOException {
        //SelectionKey是和channel对应的， selector.keys() 获取到所有在selector上注册的key
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {
            Channel channel = key.channel();//通过selectionKey反向获取channel
            if (channel instanceof SocketChannel && channel != self) {
                SocketChannel MymyChannel = (SocketChannel) channel;
                MymyChannel.write(ByteBuffer.wrap(msg.getBytes()));
            }
        }
    }

    public void readMsgFromClient() {
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {
            Channel channel = key.channel();//通过selectionKey反向获取channel
            if (channel instanceof SocketChannel) {
                SocketChannel MymyChannel = (SocketChannel) channel;
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                try {
                    int read = MymyChannel.read(byteBuffer);
                    if (read > 0) {
                        String msg = new String(byteBuffer.array());
                        System.out.println("从客户端获取到的消息是：" + msg);
                        sendMsg2OtherChannel(msg, MymyChannel);
                    }
                } catch (IOException e) {
                    try {
                        System.out.println(MymyChannel.getRemoteAddress() + "下线了");
                        MymyChannel.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }

            }
        }

    }

    public static void main(String[] args) throws IOException {
        ChatServer chatServer = new ChatServer();
        chatServer.AcceptClientConnect();
    }
}
