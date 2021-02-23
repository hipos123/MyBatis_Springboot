package com.yaoxj.nio.model.single;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-02-22 17:06
 **/
public class NIOServer {
    public static void main(String[] args) throws Exception {

        // 1.创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        // 2.创建Selector，并ServerSocketChannel注册OP_ACCEPT事件，接收连接。
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 3.开启轮询
        while (selector.select() > 0) {
            // 从selector所有事件就绪的key，并遍历处理。
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(selectionKey -> {
                SocketChannel client;
                try {
                    if (selectionKey.isAcceptable()) {  // 接受事件就绪
                        // 获取serverSocketChannel
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        // 接收连接
                        client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {  // 读事件就绪
                        // 获取socketChannel
                        client = (SocketChannel) selectionKey.channel();
                        // 创建buffer,并将获取socketChannel中的数据读入到buffer中
                        ByteBuffer readBuf = ByteBuffer.allocate(1024);
                        int readCount = client.read(readBuf);
                        if (readCount <= 0) {
                            return;
                        }
                        Charset charset = Charset.forName(StandardCharsets.UTF_8.name());
                        readBuf.flip();
                        System.out.println(String.valueOf(charset.decode(readBuf).array()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selectionKeys.remove(selectionKey);
            });
        }
    }
}
