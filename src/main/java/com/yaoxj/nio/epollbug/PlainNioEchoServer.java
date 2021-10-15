package com.yaoxj.nio.epollbug;

/**
 * @description:运行程序后，客户端连接进来，什么工作都不做，但CPU利用率却已经达到100%
 * Squashing the famous epoll bug（压碎著名的epoll bug)
 *     Linux-like OSs的选择器使用的是epoll-IO事件通知工具。操作系统使用这一高性能的技术与网络协议栈异步工作。
 *     不幸的是，即使是现在，著名的epoll-bug也可能会导致无效的状态选择和100%的CPU利用率。要解决epoll-bug的唯一方法是回收旧的选择器，将先前注册的通道实例转移到新创建的选择器上。
 * @author: yaoxj
 * @create: 2021-02-23 10:45
 **/
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public class PlainNioEchoServer {
    public static void main(String[] args) throws IOException {
        serve(8118);
    }


    public static void serve(int port) throws IOException {
        System.out.println("Listening for connections on port " + port);
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        ServerSocket ss = serverChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        ss.bind(address);
        serverChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        Set readyKeys = selector.selectedKeys();
        while (true) {
            try {
                // 这里发生的是，不管有没有已选择的SelectionKey，Selector.select()方法总是不会阻塞并且会立刻返回。
                // 这违反了Javadoc中对Selector.select()方法的描述，
                // Javadoc中的描述：Selector.select() must not unblock if nothing is selected.
                // (Selector.select()方法若未选中任何事件将会阻塞。)
                //selector.select(); 这个方法是阻塞的;selector.select(10)  10毫秒没收到连接，就不阻塞了
                System.out.println(".............");
                int select = selector.select(10);
                System.out.println("selected keys:" + select);
            }
            catch (IOException ex) {
                ex.printStackTrace();
                // handle in a proper way
                break;
            }

            Iterator iterator = readyKeys.iterator();
            // 该值将永远是假的，代码将持续消耗你的CPU资源。
            //这会有一些副作用，因为CPU消耗完了就无法再去做其他任何的工作。
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ,
                                ByteBuffer.allocate(100));
                    }
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        client.read(output);
                    }
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        output.flip();
                        client.write(output);
                        output.compact();
                    }
                }
                catch (IOException ex) {
                    key.cancel();
                    try {
                        key.channel().close();
                    }
                    catch (IOException cex) {
                    }
                }
            }
        }
    }
}
