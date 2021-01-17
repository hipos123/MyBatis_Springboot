package com.yaoxj.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
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
    String username=null;

    public ChatClient() throws IOException {
        selector = Selector.open();
        //连接服务器
        socketChannel =  SocketChannel.open(new InetSocketAddress(HOST,PORT));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //将channel注册到selector上
        socketChannel.register(selector, SelectionKey.OP_READ);
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is ok....");
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
                iterator.remove();
            }

        }
    }

    public void writeInfo2Server(String msg) throws IOException {
        String str=username+"说:"+msg;
        ByteBuffer byteBuffer=ByteBuffer.wrap(str.getBytes());
        socketChannel.write(byteBuffer);
    }

    public static void main(String[] args) throws IOException {
        ChatClient client=new ChatClient();
        new Thread() {
            @Override
            public void run() {
                while(true){
                    try {
                        client.readInfo();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();

        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String s = scanner.nextLine();
            client.writeInfo2Server(s);
        }
    }






}
